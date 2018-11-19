package com.clients.api.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderApi {

    private Long id;
    private List<ProductApi> products;
    private Long clientId;
    private Date orderDate = new Date();
}
