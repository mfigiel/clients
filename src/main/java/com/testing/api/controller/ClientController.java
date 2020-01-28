package com.testing.api.controller;

import com.testing.api.mapping.ClientApiClientMapperImpl;
import com.testing.api.resource.ClientApi;
import com.testing.repository.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class ClientController {

    private final ClientRepository clientRepository;
    ClientApiClientMapperImpl orderApiOrderMapper = new ClientApiClientMapperImpl();
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/clients")
    public List<ClientApi> getOrders() {
        return (List) clientRepository.findAll();
    }

    @PostMapping("/clients")
    void addOrder(@RequestBody ClientApi client) {
        clientRepository.save(orderApiOrderMapper.orderApiToOrderDto(client));
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ClientApi getOrderInformation(@PathVariable("id") long id) {
        Optional<ClientApi> order = Optional.ofNullable(orderApiOrderMapper.orderDtoToOrderApi(clientRepository.findById(id)));
        return order.get();
    }

}
