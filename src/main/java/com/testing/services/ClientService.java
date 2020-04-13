package com.testing.services;

import com.testing.api.mapping.ClientApiClientMapper;
import com.testing.api.resource.ClientApi;
import com.testing.repository.ClientRepository;
import com.testing.repository.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    ClientApiClientMapper clientApiClientMapper;

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
                .orElseThrow(() -> new EntityNotFoundException("client id: " + id)));

        return clientApiClientMapper.clientDtoToClientApi(client.get());
    }
}
