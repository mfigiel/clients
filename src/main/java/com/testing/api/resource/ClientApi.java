package com.testing.api.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientApi {

    private Long id;

    @NotEmpty(message = "Name is mandatory and must not be blank.")
    private String name;

    @NotEmpty(message = "Surname is mandatory and must not be blank.")
    private String Surname;

    @NotEmpty(message = "Address is mandatory and must not be blank.")
    private AddressApi address;

}
