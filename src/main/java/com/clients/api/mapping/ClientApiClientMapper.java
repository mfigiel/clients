package com.clients.api.mapping;

import com.clients.api.resource.ClientApi;
import com.clients.repository.entity.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientApiClientMapper {
    ClientApi clientDtoToClientApi(Client source);
    Client clientApiToClientDto(ClientApi source);
    List<ClientApi> clientListToClientApiList(List<Client> source);
}

