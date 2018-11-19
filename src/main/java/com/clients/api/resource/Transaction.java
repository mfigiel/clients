package com.clients.api.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Transaction {
    OrderApi order;
    ClientApi client;
    boolean finished;

    @Override
    public String toString() {
        return "Transaction{" +
                "order=" + order +
                ", client=" + client +
                ", finished=" + finished +
                '}';
    }
}
