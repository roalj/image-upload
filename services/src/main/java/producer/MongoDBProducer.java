package producer;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class MongoDBProducer {

    @Produces
    public MongoClient mongoClient() {
        //return new MongoClient(new MongoClientURI("mongodb+srv://pastafarian:goldi1@cluster0-mtykz.gcp.mongodb.net/test?retryWrites=true&w=majority"), mongoOptions());
        MongoClientURI mongoClientURI= new MongoClientURI("mongodb+srv://pastafarian:goldi1@cluster0-mtykz.gcp.mongodb.net/test?retryWrites=true&w=majority", mongoOptions());
        return new MongoClient(mongoClientURI);
    }
    public MongoClientOptions.Builder mongoOptions() {
        return MongoClientOptions
                .builder()
                .maxConnectionIdleTime(60000);
    }
}
