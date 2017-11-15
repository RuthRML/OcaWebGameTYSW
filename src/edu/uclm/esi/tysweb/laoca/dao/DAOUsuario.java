package edu.uclm.esi.tysweb.laoca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.BsonDocument;
import org.bson.BsonString;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.mongodb.MongoBroker;

public class DAOUsuario {

	public static boolean existe(String nombreJugador) throws Exception {
		
		MongoBroker broker = MongoBroker.get();
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString(nombreJugador));
		
		MongoDatabase db = broker.getDatabase("OCA");
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
		
		BsonDocument usuario = usuarios.find(criterio).first();
		
		return usuario != null;
	}
	
	

	public static void registrar(String nombreUsuario, String email, String pwd) {
		// TODO Auto-generated method stub
		
	}

}
