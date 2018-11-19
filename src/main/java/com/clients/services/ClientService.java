package com.clients.services;

import com.clients.api.mapping.ClientApiClientMapper;
import com.clients.api.resource.ClientApi;
import com.clients.repository.ClientRepository;
import com.clients.repository.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientApiClientMapper clientApiClientMapper;

    public List<ClientApi> getClients() {
        return clientApiClientMapper.clientListToClientApiList((List<Client>) clientRepository.findAll());
    }

    public ClientApi addClient(ClientApi client) {
        Client clientDto = clientApiClientMapper.clientApiToClientDto(client);
        clientDto.getAddress().setClient(clientDto);
        clientRepository.save(clientDto);
        return clientApiClientMapper.clientDtoToClientApi(clientDto);
    }

    public ClientApi getClient(long id) {
        Optional<Client> client = Optional.ofNullable(clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "client id: " + id)));

        return clientApiClientMapper.clientDtoToClientApi(client.orElse(null));
    }
}
