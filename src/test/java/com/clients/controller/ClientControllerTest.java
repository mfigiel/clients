package com.clients.controller;

import com.clients.api.resource.ClientApi;
import com.clients.repository.ClientRepository;
import com.clients.repository.entity.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sampleDataForTests.SampleClientData;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ClientControllerTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ClientRepository clientRepository;

    private SampleClientData sampleClientData = new SampleClientData();

    @Test
    public void getClient_NoResults() throws Exception {
        mockMvc
                .perform(get("/client", 1)
                        .param("id", "1"))
                .andExpect(status().is4xxClientError());

        assertThat(this.testRestTemplate.getForObject(HTTP_LOCALHOST + port + "/client/?id=1",
                String.class)).isNotNull();
    }

    @Test
    public void getClient() {
        // act

        Client client = sampleClientData.getTestClient();

        clientRepository.save(client);

        ClientApi clientApi = testRestTemplate.getForObject(HTTP_LOCALHOST + port + "/client/".concat(client.getId().toString()), ClientApi.class);

        assertEquals("sampleName", clientApi.getName());
        assertEquals("sampleSurname", clientApi.getSurname());
        assertEquals("Gliwice", clientApi.getAddress().getCity());
        assertEquals("Zwycięstwa", clientApi.getAddress().getStreet());
        assertEquals("44-100", clientApi.getAddress().getZipCode());
        assertEquals(5, clientApi.getAddress().getFlatNumber());
        assertEquals(65, clientApi.getAddress().getHouseNumber());
    }

    @Test
    public void getClients() {
        // act

        clientRepository.save(sampleClientData.getTestClient());
        clientRepository.save(sampleClientData.getTestClient());
        ResponseEntity<List<ClientApi>> clientsFromDb = testRestTemplate.exchange(HTTP_LOCALHOST + port + "/clients",  HttpMethod.GET, null, new ParameterizedTypeReference<List<ClientApi>>() {
        });

        // assert
        assertTrue(clientsFromDb.getBody().size() > 0);
        ClientApi clientApi = clientsFromDb.getBody().get(0);
        assertEquals("sampleName",clientApi.getName());
        assertEquals("sampleSurname", clientApi.getSurname());
        assertEquals("Gliwice", clientApi.getAddress().getCity());
        assertEquals("Zwycięstwa", clientApi.getAddress().getStreet());
        assertEquals("44-100", clientApi.getAddress().getZipCode());
        assertEquals(5, clientApi.getAddress().getFlatNumber());
        assertEquals(65, clientApi.getAddress().getHouseNumber());

        ClientApi secondClient = clientsFromDb.getBody().get(1);
        assertEquals("sampleName", secondClient.getName());
        assertEquals("sampleSurname", secondClient.getSurname());
        assertEquals("Gliwice", secondClient.getAddress().getCity());
        assertEquals("Zwycięstwa", secondClient.getAddress().getStreet());
        assertEquals("44-100", secondClient.getAddress().getZipCode());
        assertEquals(5, secondClient.getAddress().getFlatNumber());
        assertEquals(65, secondClient.getAddress().getHouseNumber());
    }

    @Test
    public void addClient() {
        // act
        ClientApi client = sampleClientData.getTestClientApi();
        ClientApi addedClient = testRestTemplate.postForObject(HTTP_LOCALHOST + port + "/clients", client, ClientApi.class);

        Optional<Client> clientFromDb = clientRepository.findById(addedClient.getId());

        Client clientApi = clientFromDb.get();
        // assert
        assertEquals("sampleName", clientApi.getName());
        assertEquals("sampleSurname", clientApi.getSurname());
        assertNotNull(clientApi.getAddress());
    }
}
