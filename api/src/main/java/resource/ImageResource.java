package resource;

import beans.ImageBean;
import beans.MongoConnection;
import beans.MongoHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entities.Image;
import entities.ImageEntity;
import entities.ObjectIdHolder;
import entities.ResponseHolder;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
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
        System.out.println("is ObjectID id: " + id.toString());
        boolean isSaved = imageBean.saveDocument(document);
        String resultSaving = imageBean.saveImageToCatalog(new ImageEntity(id.toString(), image.getTitle()));
        System.out.println("is saveImageToCatalog id: " + resultSaving);

        System.out.println("response: " + isSaved);
        return Response.ok(new ResponseHolder(isSaved, resultSaving)).build();
    }

    /*@GET
    @Path("/getComments/{imageId}")
    public Response getComments(@PathParam("imageId") Integer imageId) {

        int count = imageBean.getCommentCount(imageId);
        return Response.ok("Comment count" + count).build();
    }*/

    @GET
    @Path("/getImage/{mongoId}")
    public Response getImage(@PathParam("mongoId") String mongoId) {
        System.out.println("id: " + mongoId);
        Document document = new Document("_id", new ObjectId(mongoId));
        String response = imageBean.getDocument(document);
        return Response.ok(response).build();
    }

    @GET
    @Path("/getImageString/{mongoId}")
    public String getImageString(@PathParam("mongoId") String mongoId) {
        System.out.println("id: " + mongoId);
        Document document = new Document("_id", new ObjectId(mongoId));
        String imageString = imageBean.getDocument1(document);
        return imageString;
    }

    public static boolean isIsTestCheckOn() {
        return isTestCheckOn;
    }
}
