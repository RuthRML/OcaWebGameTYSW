package edu.uclm.esi.tysweb.laoca.mongodb;

import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class MongoBroker {
	
	private MongoClient mongoClient;
	
	private MongoBroker() {
		//this.mongoClient=new MongoClient("alarcosj.esi.uclm.es",27017);
		this.mongoClient=new MongoClient("localhost",27017);
		//Mirar como se pasan las credenciales
		//MongoCredential credentialsList = MongoCredential
		System.out.println("hey");
	}
	
	public static void main(String[] args) {
		MongoBroker broker = MongoBroker.get();
		MongoDatabase db = broker.mongoClient.getDatabase("laoca");
		
		if(db.getCollection("usuarios")==null) {
			db.createCollection("usuarios");
			System.out.println("No habia tabla usuarios, supuestamente se a creado una");
		}
		System.out.println("Supuestamente ya tengo la tabla de usuarios");
		
		MongoCollection<BsonDocument>usuarios=db.getCollection("usuarios",BsonDocument.class);
		
		
		
		for (int i = 0; i < 100; i++) {
			BsonDocument pepe = new BsonDocument();
			//el BsonDocument es fuertemente tipado, asi que hay que colocar el tipo de dato
			String alejandro = "alejandro" + i + "@pepe.com";
			pepe.put("email", new BsonString(alejandro));
			pepe.put("pwd",new BsonString("pepe"));
			usuarios.insertOne(pepe);
		}
		
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString("pepe100@pepe.com"));
		FindIterable<BsonDocument> busqueda = usuarios.find(criterio);
		
		BsonDocument elementoBuscado = busqueda.first();
		System.out.println(elementoBuscado.getString("email"));
		
		// Cerrar la conexión con la base de datos
		broker.mongoClient.close();
		
		
	}
	
	private static class MongoBrokerHolder{
		static MongoBroker singleton = new MongoBroker();
	}

	public static MongoBroker get() {
		return MongoBrokerHolder.singleton;
	}
	
	public  MongoDatabase getDatabase(String databaseName) {
		// TODO Auto-generated method stub
		return mongoClient.getDatabase(databaseName);
		
		
	}

	public void close() {
		mongoClient.close();
		
	}
	
	
}
