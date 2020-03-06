package com.testing.services;

import com.testing.api.resource.ClientApi;
import com.testing.repository.ClientRepository;
import com.testing.repository.entity.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class ClientServiceClass {

    @TestConfiguration
    static class ClientServiceImplTestContextConfiguration {

        @Bean
        public ClientService clientService() {
            return new ClientService();
        }
    }

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Before
    public void setUp() {
        Client client = new Client();
        client.setId(1L);
        client.setName("sampleName");

        Mockito.when(clientRepository.findById((long) 1))
                .thenReturn(Optional.of(client));

        List<Client> clientList = new ArrayList<>();

        Client clientSecond = new Client();
        clientSecond.setId(2L);
        clientSecond.setName("sampleName2");
        clientList.add(client);
        clientList.add(clientSecond);
        Mockito.when(clientRepository.findAll())
                .thenReturn(clientList);
    }

    @Test
    public void getOneClient() {
        ClientApi found = clientService.getClient(1);

        assertThat(found.getName())
                .isEqualTo("sampleName");
    }

    @Test
    public void getAllClients() {
        List<ClientApi> found = clientService.getClients();

        assertEquals(found.size(), 2);
        assertThat(found.get(0).getName())
                .isEqualTo("sampleName");
        assertThat(found.get(1).getName())
                .isEqualTo("sampleName2");
    }
}
