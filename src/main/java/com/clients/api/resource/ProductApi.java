package com.clients.api.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductApi {

    private Long id;
    private String name;
    private BigDecimal unitPrice;
    private String description;
    private String category;
    private Long unitsInStock;
    private Long unitsInOrder;
    private ProductState state = ProductState.NONE;

}
