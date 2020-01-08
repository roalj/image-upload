package com.aljosa;

import beans.MongoHelper;
import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;

@RegisterService
@ApplicationPath("api")
public class Application extends javax.ws.rs.core.Application {
    /*private MongoHelper mongoHelper;

    public com.aljosa.Application() {
        this.mongoHelper = new MongoHelper();
        System.out.println("heeeelo consturt");
    }

    public MongoHelper getMongoHelper() {
        return mongoHelper;
    }*/
}
