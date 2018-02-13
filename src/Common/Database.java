package Common;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Cursor;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;
import com.mongodb.DB;

public class Database {
	public static final String BD_Name = "adventure";
	public static final String HOST = "localhost";
	public static final int PORT = 27017;
	private static final Database MongoUtils = null;

	private static MongoClient getMongoClient() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(HOST, PORT);
		return mongoClient;
	}
	public static void connectDatabase () throws Exception {
		MongoClient mongoClient = MongoUtils.getMongoClient();

		DB db = mongoClient.getDB("adventure");
	}
	public static void searchQuery(String name, String value){
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put(name, value);
	}
	public static void selectDB (String collection) throws UnknownHostException {
		MongoClient mongoClient = MongoUtils.getMongoClient();
		DB db = mongoClient.getDB("adventure");
		DBCollection coll = db.getCollection(collection);

		/*BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("permission",3);
		searchQuery.put("name","Khám phá các địa điểm ở Hòa Bình");
		searchQuery.put("start_position","Hà Đông, Hà Nội, Việt Nam");


		Cursor cursor = coll.find(searchQuery);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		/*while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		String[] array = {"permission","name","description","start_position",
				"destination_summary","start_at","end_at","expense","amount_people","vehicles","prepare","note"};
		Cursor cursor = coll.find();
		int i = 1;
		while (cursor.hasNext()) {
			System.out.println("Document: " + i);
			System.out.println(cursor.next());
			i++;
		}*/
	}
	
	//Demo
		public static void main(String[] args) throws UnknownHostException {
		/*MongoClient mongoClient = MongoUtils.getMongoClient();

		DB db = mongoClient.getDB("adventure");

		DBCollection users = db.getCollection("trips");

		// Truy vấn tất cả các object trong Collection.
		Cursor cursor = users.find();
		int i = 1;
		while (cursor.hasNext()) {
			System.out.println("Document: " + i);
			System.out.println(cursor.next());
			i++;
		}*/
		selectDB("trips");

	}

}
