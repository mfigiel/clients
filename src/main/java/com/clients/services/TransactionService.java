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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableBinding(Sink.class)
@Slf4j
@Service
public class TransactionService {

    private String TRANSACTION_COLLECTION;
    private MongoTemplate mongoTemplate;
    private ObjectMapper objectMapper;

    public TransactionService(MongoTemplate mongoTemplate, ConfiguredObjectMapper objectMapper) {
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper.getObjectMapper();
        TRANSACTION_COLLECTION = ((System.getenv().get("COLLECTION_TEST") != null) ? "Transaction-test" : "Transaction");
        log.info(String.format("collection name: %s", TRANSACTION_COLLECTION));

    }

    @StreamListener(target = Sink.INPUT)
    public void processTransaction(Transaction transaction) {
        mongoTemplate.save(convertTransactionToMongoDocument(transaction), TRANSACTION_COLLECTION);
        log.info("Transaction saved");
    }

    public List<Object> getAllClientTransaction(Long id) {
        Query query = new Query(Criteria
                .where("client.id").is(id));
        List<Object> transactions = mongoTemplate.find(query, Object.class, TRANSACTION_COLLECTION);
        if (CollectionUtils.isEmpty(transactions)) {
            return new ArrayList<>();
        }
        return transactions;
    }

    public List<Object> getAllClientTransactionWithDatePeriod(Long id, String dateFrom, String dateTo) {
        Query query = new Query(Criteria
                .where("client.id").is(id)
                .andOperator(
                        Criteria.where("order.orderDate").gte(dateFrom),
                        Criteria.where("order.orderDate").lt(dateTo)
                )
        );
        return mongoTemplate.find(query, Object.class, TRANSACTION_COLLECTION);
    }

    private Document convertTransactionToMongoDocument(Transaction transaction) {
        return new Document(objectMapper.convertValue(transaction, Map.class));
    }

}
