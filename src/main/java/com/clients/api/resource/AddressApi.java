package com.clients.api.resource;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressApi {

    private String city;
    private String street;
    private Integer houseNumber;
    private Integer flatNumber;
    private String zipCode;
}
