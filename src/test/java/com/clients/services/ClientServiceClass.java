package com.clients.services;

import com.clients.api.mapping.ClientApiClientMapper;
import com.clients.api.resource.ClientApi;
import com.clients.repository.ClientRepository;
import com.clients.repository.entity.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import sampleDataForTests.SampleClientData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class ClientServiceClass {

    @TestConfiguration
    static class ClientServiceImplTestContextConfiguration {
        @Autowired
        private ClientRepository clientRepository;
        @Autowired
        private ClientApiClientMapper clientApiClientMapper;

        @Bean
        public ClientService clientService() {
            return new ClientService(clientRepository, clientApiClientMapper);
        }
    }

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    private SampleClientData sampleClientData = new SampleClientData();

    @Before
    public void setUp() {

        Mockito.when(clientRepository.findById((long) 1))
                .thenReturn(Optional.of(sampleClientData.getTestClient()));

        List<Client> clientList = new ArrayList<>();

        clientList.add(sampleClientData.getTestClient());
        clientList.add(sampleClientData.getTestClient());

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

        assertEquals(2, found.size());
        assertThat(found.get(0).getName())
                .isEqualTo("sampleName");
        assertThat(found.get(1).getName())
                .isEqualTo("sampleName2");
    }

    @Test
    public void addClient() {
        ClientApi added = clientService.addClient(sampleClientData.getTestClientApi());

        assertEquals("sampleName", added.getName());
        assertEquals("sampleSurname", added.getSurname());
        assertEquals("Gliwice", added.getAddress().getCity());
        assertEquals("Zwycięstwa", added.getAddress().getStreet());
        assertEquals("44-100", added.getAddress().getZipCode());
        assertEquals(5, added.getAddress().getFlatNumber());
        assertEquals(65, added.getAddress().getHouseNumber());
    }

}
