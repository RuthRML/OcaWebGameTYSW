package edu.uclm.esi.tysweb.laoca.dao;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.bson.BsonDocument;
import org.bson.BsonString;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.dominio.Usuario;
import edu.uclm.esi.tysweb.laoca.dominio.UsuarioRegistrado;
import edu.uclm.esi.tysweb.laoca.mongodb.MongoBroker;


import org.bson.BsonInt32;
public class DAOUsuario {

	public static boolean existe(String correo) throws Exception {

		MongoBroker broker = MongoBroker.get();
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString(correo));

		MongoClient conexion = broker.getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");

		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
		BsonDocument usuario = usuarios.find(criterio).first();

		// conexion.close();

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

		MongoClient client = MongoBroker.get().getDatabase("laoca", "creadorDeUsuarios", "creadorDeUsuarios");

		try {
			usuarios.insertOne(bUsuario);
			ranking.insertOne(rUsuario);
		} catch (MongoWriteException e) {
			if (e.getCode() == 11000) {
				throw new Exception("El usuario ya est\u00e1 registrado.");
			}			
			throw new Exception("Error en el registro de usuario.");
		}
		

		// conexion.close();
	}

	private static BsonString encriptar(String pwd) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest(pwd.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);

		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return new BsonString(hashtext);

	}

	/*
	 * private static void crearComoUsuarioDeLaBD(Usuario usuario, String pwd)
	 * throws Exception { BsonDocument creacionDeUsuario = new BsonDocument();
	 * creacionDeUsuario.append("createUser", new BsonString(usuario.getCorreo()));
	 * creacionDeUsuario.append("pwd", new BsonString(pwd)); BsonDocument rol = new
	 * BsonDocument(); rol.append("role", new BsonString("JugadorDeLaOca"));
	 * rol.append("db", new BsonString("laoca")); BsonArray roles = new BsonArray();
	 * roles.add(rol); creacionDeUsuario.append("roles", roles);
	 * 
	 * MongoBroker.get().getConexionPrivilegiada().getDatabase("laoca").runCommand(
	 * creacionDeUsuario);
	 * 
	 * 
	 * BsonDocument creacionDeUsuario = new BsonDocument();
	 * creacionDeUsuario.append("user", new BsonString(usuario.getLogin()));
	 * creacionDeUsuario.append("pwd", new BsonString(pwd));
	 * 
	 * //Crear el array de roles BsonDocument rol = new BsonDocument();
	 * rol.append("role", new BsonString("JugadorDeLaOca")); rol.append("db", new
	 * BsonString("laoca"));
	 * 
	 * 
	 * BsonArray roles = new BsonArray(); roles.add(rol);
	 * creacionDeUsuario.append("roles", roles);
	 * MongoBroker.get().getDatabase("laoca", "creadorDeUsuarios",
	 * "creadorDeUsuarios").getDatabase("laoca").runCommand(creacionDeUsuario);
	 * 
	 * }
	 */

	/*
	 * public static boolean enter(Usuario usuario, String pwd1) throws Exception {
	 * boolean entro = false; if (existe(usuario.getCorreo())) { if
	 * (validaContrasenha(usuario.getCorreo(), pwd1)) {
	 * 
	 * } else { throw new Exception("Contrase\u00f1a invalida"); } } else { throw
	 * new Exception("Usuario incorrecto."); }
	 * 
	 * return entro;
	 * 
	 * }
	 */

	/*
	 * private static boolean validaContrasenha(String nombreJugador, String pwd1)
	 * throws Exception { MongoBroker broker = MongoBroker.get(); BsonDocument
	 * criterio = new BsonDocument(); criterio.append("email", new
	 * BsonString(nombreJugador));
	 * 
	 * MongoDatabase db = broker.getDatabase("laoca"); MongoCollection<BsonDocument>
	 * usuarios = db.getCollection("usuarios", BsonDocument.class); BsonDocument
	 * usuario = usuarios.find(criterio).first(); // broker.close();
	 * 
	 * return usuario != null;
	 * 
	 * }
	 */

	public static Usuario login(String correoUsuario, String pwd1) throws Exception {

		int token = correoUsuario.indexOf("@");
		Usuario usuario = null;
		
		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		BsonDocument criterio = new BsonDocument();
		BsonDocument criterio2 = new BsonDocument();
		
		if(token != -1) {						
			criterio.append("email", new BsonString(correoUsuario));
			criterio.append("pwd", encriptar(pwd1));
			criterio2.append("correo", new BsonString(correoUsuario));
		}else {
			criterio.append("nombre", new BsonString(correoUsuario));
			criterio.append("pwd", encriptar(pwd1));
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
		
		//conexion.close();
		
		return usuario;
	}
	
	
	
public static void sumarVictoriaBD(Usuario usuario) throws Exception{
		
		usuario.incVictorias();
		
		BsonDocument updateUsuario = new BsonDocument();
		BsonDocument criterio = new BsonDocument();
		
		criterio.append("nombre", new BsonString(usuario.getNombre()));
		updateUsuario.append("$set", new BsonDocument().append("victorias", new BsonInt32(usuario.getVictorias())));
		
		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		MongoCollection<BsonDocument> ranking = db.getCollection("ranking", BsonDocument.class);
		
		ranking.updateOne(criterio, updateUsuario);
		
	}

	public static void registrarNuevaPwd(Usuario usuario, String pwd, String codigo) throws Exception {

		if (coincideCodigo(usuario, codigo)) {

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set", new BasicDBObject().append("pwd", encriptar(pwd)));
			BasicDBObject searchQuery = new BasicDBObject().append("email", usuario.getCorreo());

			// BsonDocument bUsuario = new BsonDocument();

			// bUsuario.append("pwd", encriptar(pwd));

			// BsonDocument criterio = new BsonDocument();
			// criterio.append("email", new BsonString(usuario.getCorreo()));

			MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
			MongoDatabase db = conexion.getDatabase("laoca");
			MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);

			// MongoClient client = MongoBroker.get().getDatabase("laoca",
			// "creadorDeUsuarios", "creadorDeUsuarios");

			try {

				// BsonDocument usuario1 = usuarios.find(criterio).first();
				// usuario1.append("pwd", encriptar(pwd));
				// usuarios.updateOne(usuario, arg1)
				usuarios.updateOne(searchQuery, newDocument);

				// crearComoUsuarioDeLaBD(usuario, pwd);
			} catch (MongoWriteException e) {
				if (e.getCode() == 11000) {
					throw new Exception("Error 11000 en DAOUsuario.java");
				}

				throw new Exception("Error en DAOUsuario.java");
			}
		}else {
			throw new Exception("No existe tal codigo de recuperación");
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

		// conexion.close();

		return usuario != null;
	}

	public static void registrarCodigoRecuperacion(String email, String codigo) throws Exception {
		BsonDocument bCodigoRecuperacionUsuario = new BsonDocument();

		bCodigoRecuperacionUsuario.append("email", new BsonString(email));
		bCodigoRecuperacionUsuario.append("codigo", new BsonString(codigo));

		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		MongoCollection<BsonDocument> codigosrecuperacion = db.getCollection("usuarioscambiopwd", BsonDocument.class);

		MongoClient client = MongoBroker.get().getDatabase("laoca", "creadorDeUsuarios", "creadorDeUsuarios");

		try {
			codigosrecuperacion.insertOne(bCodigoRecuperacionUsuario);
			// crearComoUsuarioDeLaBD(usuario, pwd);
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
			codigosrecuperacion.dropIndex(bCodigoRecuperacionUsuario);
			// crearComoUsuarioDeLaBD(usuario, pwd);
		} catch (MongoWriteException e) {
			if (e.getCode() == 11000) {
				throw new Exception("Error 11000 en eliminar codigo de Recuperacion");
			}

			throw new Exception("Error en eliminar codigo de recuperacion .");
		}
		
	}

}
