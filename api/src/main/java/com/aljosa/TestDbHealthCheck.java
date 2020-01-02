package com.aljosa;

import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import resource.ImageResource;

import javax.enterprise.context.ApplicationScoped;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

@Liveness
@ApplicationScoped
public class TestDbHealthCheck implements HealthCheck {

    private static final String url = "https://github.com/kumuluz/kumuluzee";

    private static final Logger LOG = Logger.getLogger(TestDbHealthCheck.class.getSimpleName());
    private static boolean isUpCheck;
    @Override
    public HealthCheckResponse call() {
        if (ImageResource.isIsTestCheckOn()) {
            return HealthCheckResponse.named(TestDbHealthCheck.class.getSimpleName()).up().build();
        }else{
            return HealthCheckResponse.named(TestDbHealthCheck.class.getSimpleName()).down().build();
        }

    }
}


       /* try {

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");

           if (connection.getResponseCode() == 200) {
                return HealthCheckResponse.named(TestDbHealthCheck.class.getSimpleName()).up().build();

        } catch (Exception exception) {
            LOG.severe(exception.getMessage());
        }
        return HealthCheckResponse.named(TestDbHealthCheck.class.getSimpleName()).down().build();
    }*/