package com.clients.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PreDestroy;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    private String CONNECTION_STRING = "mongodb+srv://user:user@cluster0.yvucs.mongodb.net/webshop?retryWrites=true&w=majority";
    private static final String WEBSHOP = "webshop";

    private MongoClient mongoClient = mongoClient();

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public String getDatabaseName() {
        return WEBSHOP;
    }

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoClient, getDatabaseName());
    }

    @PreDestroy
    public void destroy() {
        mongoClient.close();
    }
}
