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
import javax.ejb.EJB;
import com.mongodb.BasicDBObject;
@ApplicationScoped
@Path("/images")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImageResource {

    @Inject
    private ImageBean imageBean;

    @GET
    public Response getImageList() {
        final List<entities.ImageEntity> imageList = imageBean.getImageList();
        //connectToMongo();
        return Response.ok(imageList).build();
    }

    @GET
    @Path("/{imageId}")
    public Response getImageMetadata(@PathParam("imageId") Integer imageId) {

        ImageEntity image = imageBean.getImage(imageId);
        if (image == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(image).build();
    }

    @POST
    @Path("/saveImage/")
    public Response saveImage(Image image) {
        System.out.println(image.toString());
        Document document = new Document("title", image.getTitle()).append("content", image.getContent());
        boolean isSaved = MongoConnection.saveDocument(document);
        return Response.ok(isSaved).build();
    }

    @POST
    @Path("/getImage/")
    public Response getImage(ObjectIdHolder objectIdHolder) {
        System.out.println("id: " + objectIdHolder.getId());
        Document document = new Document("_id", new ObjectId(objectIdHolder.getId()));
        String response = MongoConnection.getDocument(document);
        return Response.ok(response).build();
    }
}
