package resource;

import beans.ImageBean;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import entities.ImageEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
        connectToMongo();
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

    private void connectToMongo(){
        try {
            MongoClientURI uri = new MongoClientURI(
                    "mongodb+srv://pastafarian:goldi1@cluster0-mtykz.gcp.mongodb.net/test?retryWrites=true&w=majority");
            MongoClient mongoClient = new MongoClient(uri);
            MongoDatabase database = mongoClient.getDatabase("test");
            database.createCollection("hello aljosa");
            System.out.println("created collection");

        }catch (Exception e){
            System.out.println("exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
