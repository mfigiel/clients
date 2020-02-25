package com.testing.services;

import com.testing.api.mapping.ClientApiClientMapperImpl;
import com.testing.api.resource.ClientApi;
import com.testing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    ClientApiClientMapperImpl clientApiClientMapper = new ClientApiClientMapperImpl();

    public List<ClientApi> getClients() {
        return (List) clientRepository.findAll();
    }

    public void addClient(ClientApi client) {
        clientRepository.save(clientApiClientMapper.orderApiToOrderDto(client));
    }

    public ClientApi getClient(long id) {
        Optional<ClientApi> order = Optional.ofNullable(clientApiClientMapper.orderDtoToOrderApi(clientRepository.findById(id)));
        return order.get();
    }
}
