package beans;

import entities.ImageEntity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.logging.Logger;


@RequestScoped
public class ImageBean {
    private Logger log = Logger.getLogger(ImageBean.class.getName());

    //@PersistenceContext(unitName = "images-jpa")
    private EntityManager em;

    private Client httpClient;
    private String baseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrl = "http://comments:8081"; // only for demonstration
    }

    public List getImageList(){
        Query query = em.createNamedQuery("Image.getAll", ImageEntity.class);
        return query.getResultList();
    }

    public ImageEntity getImage(Integer id) {
        ImageEntity imageEntity = em.find(ImageEntity.class, id);

        if (imageEntity == null) {
            throw  new NotFoundException();
        }

        imageEntity.setCommentsCount(getCommentCount(id));

        return imageEntity;
    }

    private Integer getCommentCount(Integer imageId) {
        try {
            return httpClient
                    .target(baseUrl + "/api/comments/count")
                    .queryParam("imageId", imageId)
                    .request().get(new GenericType<Integer>() {
                    });
        } catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
}
