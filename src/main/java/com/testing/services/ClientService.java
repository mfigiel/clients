package com.testing.services;

import com.testing.api.mapping.ClientApiClientMapperImpl;
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
    ClientApiClientMapperImpl clientApiClientMapper = new ClientApiClientMapperImpl();

    public List<ClientApi> getClients() {
        return clientApiClientMapper.clientListToClientApiList((List<Client>) clientRepository.findAll());
    }

    public void addClient(ClientApi client) {
        clientRepository.save(clientApiClientMapper.clientApiToClientDto(client));
    }

    public ClientApi getClient(long id) {
        Optional<Client> client = Optional.ofNullable(clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("client id: " + id)));

        return clientApiClientMapper.clientDtoToClientApi(client.get());
    }
}
