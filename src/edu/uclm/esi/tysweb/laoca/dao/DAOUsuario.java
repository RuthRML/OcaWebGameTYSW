package edu.uclm.esi.tysweb.laoca.dao;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.bson.BsonDocument;
import org.bson.BsonString;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.dominio.Usuario;
import edu.uclm.esi.tysweb.laoca.dominio.UsuarioRegistrado;
import edu.uclm.esi.tysweb.laoca.mongodb.MongoBroker;

public class DAOUsuario {

	public static boolean existe(String nombreJugador) throws Exception {

		MongoBroker broker = MongoBroker.get();
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString(nombreJugador));

		MongoClient conexion = broker.getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");

		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
		BsonDocument usuario = usuarios.find(criterio).first();
		
		conexion.close();

		return usuario != null;

	}

	public static void registrar(Usuario usuario, String pwd) throws Exception {

		BsonDocument bUsuario = new BsonDocument();

		bUsuario.append("email", new BsonString(usuario.getCorreo()));
		bUsuario.append("nombre", new BsonString(usuario.getNombre()));
		bUsuario.append("pwd", encriptar(pwd));

		MongoClient conexion = MongoBroker.get().getConexionPrivilegiada();
		MongoDatabase db = conexion.getDatabase("laoca");
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);

		MongoClient client = MongoBroker.get().getDatabase("laoca", "creadorDeUsuarios", "creadorDeUsuarios");

		try {
			usuarios.insertOne(bUsuario);
			//crearComoUsuarioDeLaBD(usuario, pwd);
		} catch (MongoWriteException e) {
			if (e.getCode() == 11000)
				throw new Exception("El usuario ya est\u00e1 registrado.");
			throw new Exception("Error en el registro de usuario.");
		}
		
		conexion.close();
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

	/*private static void crearComoUsuarioDeLaBD(Usuario usuario, String pwd) throws Exception {
		BsonDocument creacionDeUsuario = new BsonDocument();
		creacionDeUsuario.append("createUser", new BsonString(usuario.getCorreo()));
		creacionDeUsuario.append("pwd", new BsonString(pwd));
		BsonDocument rol = new BsonDocument();
		rol.append("role", new BsonString("JugadorDeLaOca"));
		rol.append("db", new BsonString("laoca"));
		BsonArray roles = new BsonArray();
		roles.add(rol);
		creacionDeUsuario.append("roles", roles);

		MongoBroker.get().getConexionPrivilegiada().getDatabase("laoca").runCommand(creacionDeUsuario);

		
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
		 
	}*/

/*	public static boolean enter(Usuario usuario, String pwd1) throws Exception {
		boolean entro = false;
		if (existe(usuario.getCorreo())) {
			if (validaContrasenha(usuario.getCorreo(), pwd1)) {

			} else {
				throw new Exception("Contrase\u00f1a invalida");
			}
		} else {
			throw new Exception("Usuario incorrecto.");
		}

		return entro;

	}*/

/*	private static boolean validaContrasenha(String nombreJugador, String pwd1) throws Exception {
		MongoBroker broker = MongoBroker.get();
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString(nombreJugador));
		
		MongoDatabase db = broker.getDatabase("laoca");
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
		BsonDocument usuario = usuarios.find(criterio).first();
		// broker.close();

		return usuario != null;

	}*/

	public static Usuario login(String correoUsuario, String pwd1) throws Exception {
		
		int token = correoUsuario.indexOf("@");
		Usuario usuario = null;
		MongoClient conexion = MongoBroker.get().getDatabase("laoca", correoUsuario, pwd1);
		BsonDocument criterio = new BsonDocument();
		
		if(token != -1) {						
			criterio.append("email", new BsonString(correoUsuario));
			criterio.append("pwd", encriptar(pwd1));
		}else {
			criterio.append("nombre", new BsonString(correoUsuario));
			criterio.append("pwd", encriptar(pwd1));
		}
		
		MongoCollection<BsonDocument> usuarios = conexion.getDatabase("laoca").getCollection("usuarios", BsonDocument.class);
		FindIterable<BsonDocument> resultado = usuarios.find(criterio);
		BsonDocument usuarioLog = resultado.first();
		
		if (usuarioLog != null) {			
			usuario = new UsuarioRegistrado();			
			usuario.setCorreo(usuarioLog.getString("email").toString());
			usuario.setNombre(usuarioLog.getString("nombre").toString());
		}else {
			throw new Exception("Contrase\u00f1a inv\u00e1lida o no existe el usuario.");
		}
		
		conexion.close();
		
		return usuario;
	}

}
