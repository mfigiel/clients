package com.testing.services;

import com.testing.api.resource.ClientApi;
import com.testing.repository.ClientRepository;
import com.testing.repository.entity.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class ClientServiceClass {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository orderRepository;

    @Before
    public void setUp() {
        Client client = new Client();
        client.setId(1);
        client.setName("sampleName");

        Mockito.when(orderRepository.findById((long) 1))
                .thenReturn(Optional.of(client));

        List<Client> orderList = new ArrayList<>();

        Client clientSecond = new Client();
        clientSecond.setName("sampleName2");
        orderList.add(clientSecond);
        Mockito.when(orderRepository.findAll())
                .thenReturn(orderList);
    }

    @Test
    public void getOneOrder() {
        ClientApi found = clientService.getClient(1);

        assertThat(found.getName())
                .isEqualTo("sampleName");
    }

    @Test
    public void getAllOrders() {
        List<ClientApi> found = clientService.getClients();

        assertEquals(found.size(), 2);
        assertThat(found.get(0).getName())
                .isEqualTo("sampleName");
        assertThat(found.get(1).getName())
                .isEqualTo("sampleName2");
    }
}
