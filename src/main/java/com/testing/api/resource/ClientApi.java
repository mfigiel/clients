package com.testing.api.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientApi {

    @NotNull(message = "Name is mandatory and must not be blank.")
    private String name;

    @NotNull(message = "Surname is mandatory and must not be blank.")
    private String Surname;

    @NotNull(message = "Address is mandatory and must not be blank.")
    private AddressApi address;

}
