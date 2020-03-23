package com.testing.services;

import com.testing.api.resource.AddressApi;
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

        @Bean
        public ClientService clientService() {
            return new ClientService();
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

        assertEquals(found.size(), 2);
        assertThat(found.get(0).getName())
                .isEqualTo("sampleName");
        assertThat(found.get(1).getName())
                .isEqualTo("sampleName2");
    }

    @Test
    public void addClient() {
        ClientApi added = clientService.addClient(sampleClientData.getTestClientApi());

        assertEquals(added.getName(), "sampleName");
        assertEquals(added.getSurname(), "sampleSurname");
        assertEquals(added.getAddress().getCity(), "Gliwice");
        assertEquals(added.getAddress().getStreet(), "Zwycięstwa");
        assertEquals(added.getAddress().getZipCode(), "44-100");
        assertEquals(added.getAddress().getFlatNumber(), 5);
        assertEquals(added.getAddress().getHouseNumber(), 65);
    }

}
