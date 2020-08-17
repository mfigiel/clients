package com.clients.api.resource;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressApi {

    @NotEmpty
    @Size(min=2, max=40)
    private String city;
    @NotEmpty
    @Size(min=2, max=40)
    private String street;
    private Integer houseNumber;
    private Integer flatNumber;
    @NotEmpty
    private String zipCode;
}
