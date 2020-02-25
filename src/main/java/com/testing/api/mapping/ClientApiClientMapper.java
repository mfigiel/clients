package com.testing.api.mapping;

import com.testing.api.resource.ClientApi;
import com.testing.repository.entity.Client;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "ClientApiClientMapper")
public interface ClientApiClientMapper {
    ClientApi clientDtoToClientApi(Optional<Client> source);
    Client clientApiToClientDto(ClientApi source);
}

