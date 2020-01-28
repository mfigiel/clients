package com.testing.api.mapping;

import com.testing.api.resource.ClientApi;
import com.testing.repository.entity.Client;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "ClientApiClientMapper")
public interface ClientApiClientMapper {
    ClientApi orderDtoToOrderApi(Optional<Client> source);
    Client orderApiToOrderDto(ClientApi source);
}

