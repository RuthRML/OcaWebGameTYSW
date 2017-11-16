package edu.uclm.esi.tysweb.laoca.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.dominio.Usuario;
import edu.uclm.esi.tysweb.laoca.mongodb.MongoBroker;

public class DAOUsuario {

	public static boolean existe(String nombreJugador) throws Exception {
		/*String sql = "SELECT count(*) FROM usuario WHERE email=?";
		Connection bd = DBBroker.get().getBD();
		PreparedStatement ps = bd.prepareStatement(sql);
		ps.setString(1,  nombreJugador);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int resultado = rs.getInt(1);
		DBBroker.get().close(bd);
		return resultado == 1;*/
		
		MongoBroker broker = MongoBroker.get();
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString(nombreJugador));
		MongoDatabase db = broker.getDatabase("laoca");
		MongoCollection<BsonDocument>usuarios = db.getCollection("usuarios", BsonDocument.class);
		BsonDocument usuario = usuarios.find(criterio).first();
		//broker.close();
		
		
		return usuario!=null;
		
	}
	
	

	public static void registrar(String nombreUsuario, String email, String pwd) {
		// TODO Auto-generated method stub
		
	}



	public static void insert(Usuario usuario, String pwd) throws Exception{
		

		BsonDocument bUsuario = new BsonDocument();

		bUsuario.append("email", new BsonString(usuario.getLogin()));
		bUsuario.append("pwd",encriptar(pwd));
		MongoDatabase db;
		try {
		db= MongoBroker.get().getDatabase("laoca");
		}catch(Exception e) {
			throw new Exception("No se pudo conectar a la base de datos");
		}
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios",BsonDocument.class);
		
		try {
		usuarios.insertOne(bUsuario);
		crearComoUsuarioDeLaBD( usuario,  pwd);
		}catch(MongoWriteException e) {
			if(e.getCode()==11000) 
				throw new Exception("¿No estarás ya registrado, chaval/chavala?");
			throw new Exception("Quien sabe qué pasó");
		}
	}



	private static BsonString encriptar(String pwd) throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD5");
		 byte[] messageDigest = md.digest(pwd.getBytes());
		 BigInteger number = new BigInteger(1, messageDigest);
		 String hashtext = number.toString(16);
		 
		 while (hashtext.length() < 32) {
		 hashtext = "0" + hashtext;
		 }
		 return new BsonString(hashtext);
		
	}
	
	private static void crearComoUsuarioDeLaBD(Usuario usuario, String pwd) {
		
		
		BsonDocument creacionDeUsuario = new BsonDocument();
		creacionDeUsuario.append("user", new BsonString(usuario.getLogin()));
		creacionDeUsuario.append("pwd", new BsonString(pwd));
		
		//Crear el array de roles
		BsonDocument rol = new BsonDocument();
		rol.append("role", new BsonString("JugadorDeLaOca"));
		rol.append("db", new BsonString("laoca"));
		
		
		BsonArray roles = new BsonArray();
		roles.add(rol);
		creacionDeUsuario.append("roles", roles);
		MongoBroker.get().getDatabase("laoca").runCommand(creacionDeUsuario);
	}



	public static boolean enter(Usuario usuario, String pwd1) throws Exception {
		boolean entro=false;
			if(existe(usuario.getLogin())) {
				if(validaContrasenha(usuario.getLogin(),pwd1)) {
					
				}else {
					throw new Exception("Contraseña invalida");
				}
			}else {
				throw new Exception("Usuario incorrecto");
			}
				
				
		return entro;	
		
		
	}



	private static boolean validaContrasenha(String nombreJugador,String pwd1) throws Exception {
		MongoBroker broker = MongoBroker.get();
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString(nombreJugador));
		criterio.append("pwd",encriptar(pwd1));
		MongoDatabase db = broker.getDatabase("laoca");
		MongoCollection<BsonDocument>usuarios = db.getCollection("usuarios", BsonDocument.class);
		BsonDocument usuario = usuarios.find(criterio).first();
		//broker.close();
		
		
		return usuario!=null;

	}
	
	

}
