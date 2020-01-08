package beans;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entities.Image;
import entities.ImageEntity;
import org.bson.Document;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@ApplicationScoped
public class ImageBean {
    private Logger log = Logger.getLogger(ImageBean.class.getName());

    //@PersistenceContext(unitName = "images-jpa")
    private EntityManager em;

    private Client httpClient;
    //private String baseUrl;

    @Inject
    private MongoClient mongoClient;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        //baseUrl = "http://comments:8081"; // only for demonstration
    }

    @Inject
    @DiscoverService(value = "image-catalog-services", environment = "dev", version = "1.0.0")
    private Optional<String> baseUrlImageCatalog;

    @Inject
    @DiscoverService(value = "comments-service", environment = "dev", version = "1.0.0")
    private Optional<String> baseUrl;


    public boolean saveDocument(Document document){
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("images");
        collection.insertOne(document);
        return true;
    }

    public String getDocument(Document doc){
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("images");
        FindIterable<Document> documents = null;

        String json = "";

            BasicDBObject fields = new BasicDBObject();
            fields.put("content", 1);
            fields.put("_id", 0);

            Document document = collection.find(doc).projection(fields).first();

        if (document != null) {
            json = document.toJson();
        }
        return json;
    }

    public String getDocument1(Document doc){
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("images");
        FindIterable<Document> documents = null;

        String json = "";

        BasicDBObject fields = new BasicDBObject();
        fields.put("content", 1);
        fields.put("_id", 0);

        Document document = collection.find(doc).projection(fields).first();
        if (document != null) {
            json = document.getString("content");//document.toJson();
        }
        return json;
    }

    public String saveImageToCatalog(ImageEntity image) {
        //za test klici direktno na server
        if (baseUrlImageCatalog.isPresent()) {
        log.info("test2");
        log.info("Calling baseUrlImageCatalog service:saving image. " + image.getMongoId());
            log.info("baseUrlImageCatalog " + baseUrlImageCatalog.get() + "/api/images");
            try {
                return httpClient.target(baseUrlImageCatalog.get() + "/api/images")
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.json(image), String.class)
                        ;
            } catch (WebApplicationException | ProcessingException e) {
                log.severe(e.getMessage());
                throw new InternalServerErrorException(e);
            }
        }
        return null;
    }

    //@Fallback(fallbackMethod = "getCommentCountFallback")
    public Integer getCommentCount(Integer imageId) {
        if (baseUrl.isPresent()) {
            log.info("Calling comments service: getting comment count. " + baseUrl);
            try {
                return httpClient
                        .target(baseUrl.get() + "/api/comments/count")
                        .queryParam("imageId", imageId)
                        .request().get(new GenericType<Integer>() {
                        });
            } catch (WebApplicationException | ProcessingException e) {
                log.severe(e.getMessage());
                throw new InternalServerErrorException(e);
            }
        }
        return null;
    }
}
