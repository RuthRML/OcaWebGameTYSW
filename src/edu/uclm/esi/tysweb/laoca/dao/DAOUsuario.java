package edu.uclm.esi.tysweb.laoca.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Iterator;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.json.JSONArray;
import org.bson.BsonInt32;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.dominio.Usuario;
import edu.uclm.esi.tysweb.laoca.dominio.UsuarioRegistrado;
import edu.uclm.esi.tysweb.laoca.mongodb.MongoBroker;



public class DAOUsuario {
	
	public JSONArray getRanking() throws Exception{
		JSONArray lista = new JSONArray();
		
		MongoBroker broker = MongoBroker.get();
		MongoClient conexion = broker.getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		
		MongoCollection<BsonDocument> ranking = db.getCollection("ranking", BsonDocument.class);
		FindIterable<BsonDocument> resultado = ranking.find();
		Iterator<BsonDocument> listaI = resultado.iterator();
		
		while(listaI.hasNext()) {
			lista.put(listaI.next());
		}
		
		return lista;
	}

	public static boolean existe(String correo) throws Exception {

		MongoBroker broker = MongoBroker.get();
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString(correo));

		MongoClient conexion = broker.getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");

		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
		BsonDocument usuario = usuarios.find(criterio).first();

		return usuario != null;

	}

	public static void registrar(Usuario usuario, String pwd) throws Exception {

		BsonDocument bUsuario = new BsonDocument();
		BsonDocument rUsuario = new BsonDocument();

		bUsuario.append("email", new BsonString(usuario.getCorreo()));
		bUsuario.append("nombre", new BsonString(usuario.getNombre()));
		bUsuario.append("pwd", encriptar(pwd));
		
		rUsuario.append("nombre", new BsonString(usuario.getNombre()));
		rUsuario.append("correo", new BsonString(usuario.getCorreo()));
		rUsuario.append("victorias", new BsonInt32(0));

		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
		MongoCollection<BsonDocument> ranking = db.getCollection("ranking", BsonDocument.class);

		try {
			usuarios.insertOne(bUsuario);
			ranking.insertOne(rUsuario);
		} catch (MongoWriteException e) {
			if (e.getCode() == 11000) {
				throw new Exception("El usuario ya est\u00e1 registrado.");
			}			
			throw new Exception("Error en el registro de usuario.");
		}

	}

	public static BsonString encriptar(String pwd) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest(pwd.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);

		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return new BsonString(hashtext);

	}

	public static Usuario login(String correoUsuario, String pwd1) throws Exception {

		String checkCipherPwd = "cipherPass=?";
		int tokenCorreo = correoUsuario.indexOf("@");
		int tokenPwd = pwd1.indexOf(checkCipherPwd);
		Usuario usuario = null;
		
		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		BsonDocument criterio = new BsonDocument();
		BsonDocument criterio2 = new BsonDocument();
		
		if(tokenCorreo != -1) {						
			criterio.append("email", new BsonString(correoUsuario));
			
			if(tokenPwd != -1) {
				criterio.append("pwd", new BsonString(pwd1.substring(checkCipherPwd.length())));
			} else {
				criterio.append("pwd", encriptar(pwd1));
			}
			
			criterio2.append("correo", new BsonString(correoUsuario));
		}else {
			criterio.append("nombre", new BsonString(correoUsuario));
			
			if(tokenPwd != -1) {
				criterio.append("pwd", new BsonString(pwd1.substring(checkCipherPwd.length())));
			} else {
				criterio.append("pwd", encriptar(pwd1));
			}
			
			criterio2.append("nombre", new BsonString(correoUsuario));
		}
		
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
		MongoCollection<BsonDocument> ranking = db.getCollection("ranking", BsonDocument.class);
		FindIterable<BsonDocument> resultado = usuarios.find(criterio);
		FindIterable<BsonDocument> resultado2 = ranking.find(criterio2);
		BsonDocument usuarioLog = resultado.first();
		BsonDocument usuarioLog2 = resultado2.first();
		
		if (usuarioLog != null) {			
			usuario = new UsuarioRegistrado();			
			usuario.setCorreo(usuarioLog.getString("email").asString().getValue());
			usuario.setNombre(usuarioLog.getString("nombre").asString().getValue());
			usuario.setVictorias(usuarioLog2.getInt32("victorias").asInt32().getValue());
		}else {
			throw new Exception("Contrase\u00f1a inv\u00e1lida o no existe el usuario.");
		}
		
		return usuario;
	}
	
	
	
	public static void sumarVictoriaBD(Usuario usuario) throws Exception{
		
		usuario.incVictorias();
		
		BsonDocument updateUsuario = new BsonDocument();
		BsonDocument criterio = new BsonDocument();
		
		criterio.append("correo", new BsonString(usuario.getCorreo()));
		updateUsuario.append("$set", new BsonDocument().append("victorias", new BsonInt32(usuario.getVictorias())));
		
		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		MongoCollection<BsonDocument> ranking = db.getCollection("ranking", BsonDocument.class);
		
		FindIterable<BsonDocument> resultado = ranking.find(criterio);
		BsonDocument usuarioFind = resultado.first();
		
		if(usuarioFind != null) {
			ranking.updateOne(criterio, updateUsuario);
		}else {
			throw new Exception("El usuario no est\u00e1 registrado.");
		}
		
	}

	public static void registrarNuevaPwd(Usuario usuario, String pwd, String codigo) throws Exception {

		if (coincideCodigo(usuario, codigo)) {

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set", new BasicDBObject().append("pwd", encriptar(pwd)));
			BasicDBObject searchQuery = new BasicDBObject().append("email", usuario.getCorreo());

			MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
			MongoDatabase db = conexion.getDatabase("laoca");
			MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);

			try {
				usuarios.updateOne(searchQuery, newDocument);
			} catch (MongoWriteException e) {
				if (e.getCode() == 11000) {
					throw new Exception("Error 11000 en DAOUsuario.java");
				}
				throw new Exception("Error en DAOUsuario.java");
			}
		}else {
			throw new Exception("No existe tal codigo de recuperaci\u00f3n");
		}

	}

	public static boolean coincideCodigo(Usuario usuario, String codigo) {
		MongoBroker broker = MongoBroker.get();
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString(usuario.getCorreo()));
		criterio.append("codigo", new BsonString(codigo));

		MongoClient conexion = broker.getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");

		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarioscambiopwd", BsonDocument.class);
		BsonDocument usuarioconcodigo = usuarios.find(criterio).first();

		return usuario != null;
	}
	
	public static void registrarNuevaPwdVoluntario(Usuario usuario, String pwd) throws Exception {

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("pwd", encriptar(pwd)));
		BasicDBObject searchQuery = new BasicDBObject().append("email", usuario.getCorreo());

		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);

		try {
			usuarios.updateOne(searchQuery, newDocument);
		} catch (MongoWriteException e) {
			if (e.getCode() == 11000) {
				throw new Exception("Error 11000 en DAOUsuario.java");
			}
			throw new Exception("Error en DAOUsuario.java");
		}
	

}

	public static void registrarCodigoRecuperacion(String email, String codigo) throws Exception {
		BsonDocument bCodigoRecuperacionUsuario = new BsonDocument();

		bCodigoRecuperacionUsuario.append("email", new BsonString(email));
		bCodigoRecuperacionUsuario.append("codigo", new BsonString(codigo));

		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		MongoCollection<BsonDocument> codigosrecuperacion = db.getCollection("usuarioscambiopwd", BsonDocument.class);

		try {
			codigosrecuperacion.insertOne(bCodigoRecuperacionUsuario);
		} catch (MongoWriteException e) {
			if (e.getCode() == 11000) {
				throw new Exception("El codigo ya esta registrado");
			}
			throw new Exception("Error en el registro de codigo recuperacion.");
		}
		
	}

public static void eliminarCodigoRecuperacion(Usuario usuario, String pwd, String codigo) throws Exception {
		
		BsonDocument bCodigoRecuperacionUsuario = new BsonDocument();

		bCodigoRecuperacionUsuario.append("email", new BsonString(usuario.getCorreo()));
		bCodigoRecuperacionUsuario.append("codigo", new BsonString(codigo));

		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		MongoCollection<BsonDocument> codigosrecuperacion = db.getCollection("usuarioscambiopwd", BsonDocument.class);

		MongoClient client = MongoBroker.get().getDatabase("laoca", "creadorDeUsuarios", "creadorDeUsuarios");

		try {
			codigosrecuperacion.deleteOne(bCodigoRecuperacionUsuario);
		} catch (MongoWriteException e) {
			if (e.getCode() == 11000) {
				throw new Exception("Error 11000 en eliminar codigo de Recuperacion");
			}

			throw new Exception("Error en eliminar codigo de recuperacion .");
		}
		
	}

}
