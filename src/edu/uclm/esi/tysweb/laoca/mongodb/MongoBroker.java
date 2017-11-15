package edu.uclm.esi.tysweb.laoca.mongodb;

import org.bson.BsonDocument;
import org.bson.BsonString;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoBroker {

	private MongoClient mongoClient;
	
	private MongoBroker() {
		this.mongoClient = new MongoClient("localhost", 27017);
	}
	
	private static class MongoBrokerHolder{
		static MongoBroker singleton = new MongoBroker();
	}
	
	public static MongoBroker get() {
		return MongoBrokerHolder.singleton;
	}
	
	
	
	
	public static void main(String [] args){
		MongoBroker broker = MongoBroker.get();
		MongoDatabase db = broker.mongoClient.getDatabase("OCA");
		
		// Comprobar la colección o crear colección
		if (db.getCollection("usuarios") == null) {
			db.createCollection("usuarios");
		}
		
		// Coger la colección
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios", BsonDocument.class);
		
		for(int i = 0; i<100; i++) {
			// Crear Bson Document
			BsonDocument usuario = new BsonDocument();
			
			// Añadir campos al Bson Document
			usuario.put("email", new BsonString("usuario" + i + "@usuario.com"));
			usuario.put("pwd", new BsonString("usuario" + i));
			
			// Insertar Bson Document
			usuarios.insertOne(usuario);		
		}
		
		// SELECT a todos los usuarios
		FindIterable<BsonDocument> itUsuarios = usuarios.find();
		
		// SELECT un usuario
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString("usuario1@usuario.com"));
		FindIterable<BsonDocument> busqueda = usuarios.find(criterio);
		BsonDocument elementoBuscado = busqueda.first();
		System.out.println(elementoBuscado.getString("email"));
		System.out.println(elementoBuscado.getString("pwd"));
		
		// Cerrar la conexión con la base de datos
		broker.mongoClient.close();
		
	}

	public MongoDatabase getDatabase(String string) {
		return this.mongoClient.getDatabase(string);
	}

	public void close() {
		this.mongoClient.close();		
	}
	
	
}
