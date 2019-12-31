package beans;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Singleton;

@Singleton
public class MongoHelper {

    private MongoDatabase mongoDatabase;

    @PostConstruct
    public void connectToMongo() {
        System.out.println("connectToMongo");
        try {
            MongoClientURI uri = new MongoClientURI(
                    "mongodb+srv://pastafarian:goldi1@cluster0-mtykz.gcp.mongodb.net/test?retryWrites=true&w=majority");
            MongoClient mongoClient = new MongoClient(uri);
            mongoDatabase = mongoClient.getDatabase("test");
            mongoDatabase.createCollection("images");
            System.out.println("created collection");
        } catch (Exception e) {
            System.out.println("exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }
}