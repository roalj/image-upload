package beans;

import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;

import static com.mongodb.client.model.Filters.eq;

public class MongoConnection {


    public void connectToMongo(){
        try {
            MongoClientURI uri = new MongoClientURI(
                    "mongodb+srv://pastafarian:goldi1@cluster0-mtykz.gcp.mongodb.net/test?retryWrites=true&w=majority");
            MongoClient mongoClient = new MongoClient(uri);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
            //database.createCollection("hello aljosa");
            System.out.println("created collection");
        }catch (Exception e){
            System.out.println("exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static boolean saveDocument(Document document){
        MongoClient mongoClient = null;

        try {
            mongoClient = new MongoClient(getMongoClientURI());
            System.out.println("created collection");
            (getMongoConnection(mongoClient)).insertOne(document);
            System.out.println("insertOne document");

        } catch (Exception e) {
            System.out.println("exception: " + e.toString());
            e.printStackTrace();
            return  false;
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
        return true;
    }

    public static String getDocument(Document doc){
        MongoClient mongoClient = null;
        FindIterable<Document> documents = null;
        String json = "";

        try {
            mongoClient = new MongoClient(getMongoClientURI());
            Document document = (Document) (getMongoConnection(mongoClient)).find(doc).first();

            if (document != null) {
                json = document.toJson();
                System.out.println("getDocument document " + json);
            }
        } catch (Exception e) {
            System.out.println("exception: " + e.toString());
            e.printStackTrace();
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
            return json;
        }
    }

    private static MongoCollection getMongoConnection(MongoClient mongoClient){
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        return mongoDatabase.getCollection("images");

    }

    private static MongoClientURI getMongoClientURI(){
       return new MongoClientURI(
                "mongodb+srv://pastafarian:goldi1@cluster0-mtykz.gcp.mongodb.net/test?retryWrites=true&w=majority");
    }
}
