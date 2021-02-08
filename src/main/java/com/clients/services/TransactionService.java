package com.clients.services;

import com.clients.api.resource.Transaction;
import com.clients.configuration.ConfiguredObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@EnableBinding(Sink.class)
@Slf4j
@Service
public class TransactionService {

    private static final String TRANSACTION_COLLECTION = "Transaction";
    private MongoTemplate mongoTemplate;
    private ObjectMapper objectMapper;

    public TransactionService(MongoTemplate mongoTemplate, ConfiguredObjectMapper objectMapper) {
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper.getObjectMapper();
    }

    @StreamListener(target = Sink.INPUT)
    public void processTransaction(Transaction transaction) {
        mongoTemplate.save(convertTransactionToMongoDocument(transaction), TRANSACTION_COLLECTION);
        log.info("Transaction saved");
    }

    public List<Transaction> getAllClientTransaction(Long id) {
        Query query = new Query(Criteria
                .where("client.id").is(id));
        return mongoTemplate.find(query, Transaction.class, TRANSACTION_COLLECTION);
    }

    public List<Transaction> getAllClientTransactionWithDatePeriod(Long id, Date dateFrom, Date dateTo) {
        Query query = new Query(Criteria
                .where("client.id").is(id)
                .and("order.orderDate").gt(dateFrom)
                .and("order.orderDate").lt(dateTo));
        return mongoTemplate.find(query, Transaction.class, TRANSACTION_COLLECTION);
    }

    private Document convertTransactionToMongoDocument(Transaction transaction) {
        return new Document(objectMapper.convertValue(transaction, Map.class));
    }

}
