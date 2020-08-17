package com.clients.api.controller;

import com.clients.api.resource.ClientApi;
import com.clients.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/clients")
    public List<ClientApi> getClients() {
        return clientService.getClients();
    }

    @PostMapping("/clients")
    public ClientApi addClient(@RequestBody ClientApi client) {
        return clientService.addClient(client);
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ClientApi getClientInformation(@PathVariable("id") long id) {
        ClientApi a = clientService.getClient(id);
        return clientService.getClient(id);

    }

}
