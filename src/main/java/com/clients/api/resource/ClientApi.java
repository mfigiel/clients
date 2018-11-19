package com.clients.api.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientApi {

    private Long id;
    private String name;
    private String surname;
    private AddressApi address;

}
