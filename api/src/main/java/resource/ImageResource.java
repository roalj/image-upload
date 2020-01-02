package resource;

import beans.ImageBean;
import beans.MongoConnection;
import beans.MongoHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entities.Image;
import entities.ImageEntity;
import entities.ObjectIdHolder;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import com.mongodb.BasicDBObject;
@ApplicationScoped
@Path("/images")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImageResource {

    private static boolean isTestCheckOn = true;

    @Inject
    private ImageBean imageBean;

    @GET
    public Response getImageList() {
        //final List<entities.ImageEntity> imageList = imageBean.getImageList();
        String welcome = "welcome to instagram restservice6";
        return Response.ok(welcome).build();
    }

    @POST
    @Path("/setTestCheck/")
    public Response setTestHealthCheck(ObjectIdHolder objectIdHolder) {
        System.out.println("is checkOn: " + objectIdHolder.getIsCheckOn());
        isTestCheckOn = objectIdHolder.getIsCheckOn();
        return Response.ok(isTestCheckOn).build();
    }

    @POST
    @Path("/saveImage/")
    public Response saveImage(Image image) {
        System.out.println(image.toString());
        ObjectId id = new ObjectId();
        Document document = new Document("title", image.getTitle()).append("content", image.getContent()).append("_id", id);
        System.out.println("is ObjectID id: " + id);
        boolean isSaved = MongoConnection.saveDocument(document);
        String resultSaving  = "";
        if(isSaved){
            resultSaving = imageBean.saveImageToCatalog(new ImageEntity("5e0d037caf9e9d5f20de913f"));
            System.out.println("is saveImageToCatalog id: " + resultSaving);
        }

        System.out.println("response: " + isSaved);
        return Response.ok(isSaved + " resulult saving: " + resultSaving).build();
    }

    @GET
    @Path("/getComments/{imageId}")
    public Response getComments(@PathParam("imageId") Integer imageId) {

        int count = imageBean.getCommentCount(imageId);
        return Response.ok("Comment count" + count).build();
    }

    @POST
    @Path("/getImage/")
    public Response getImage(ObjectIdHolder objectIdHolder) {
        System.out.println("id: " + objectIdHolder.getId());
        Document document = new Document("_id", new ObjectId(objectIdHolder.getId()));
        String response = MongoConnection.getDocument(document);
        return Response.ok(response).build();
    }

    public static boolean isIsTestCheckOn() {
        return isTestCheckOn;
    }
}
