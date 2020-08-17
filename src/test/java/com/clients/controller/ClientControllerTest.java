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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        assertEquals(clientApi.getName(), "sampleName");
        assertEquals(clientApi.getSurname(), "sampleSurname");
        assertEquals(clientApi.getAddress().getCity(), "Gliwice");
        assertEquals(clientApi.getAddress().getStreet(), "Zwycięstwa");
        assertEquals(clientApi.getAddress().getZipCode(), "44-100");
        assertEquals(clientApi.getAddress().getFlatNumber(), 5);
        assertEquals(clientApi.getAddress().getHouseNumber(), 65);
    }

    @Test
    public void getClients() {
        // act

        clientRepository.save(sampleClientData.getTestClient());
        clientRepository.save(sampleClientData.getTestClient());
        ResponseEntity<List<ClientApi>> clientsFromDb = testRestTemplate.exchange(HTTP_LOCALHOST + port + "/clients",  HttpMethod.GET, null, new ParameterizedTypeReference<List<ClientApi>>() {
        });

        // assert
        assertThat(clientsFromDb.getBody().size() > 0);
        ClientApi clientApi = clientsFromDb.getBody().get(0);
        assertEquals(clientApi.getName(), "sampleName");
        assertEquals(clientApi.getSurname(), "sampleSurname");
        assertEquals(clientApi.getAddress().getCity(), "Gliwice");
        assertEquals(clientApi.getAddress().getStreet(), "Zwycięstwa");
        assertEquals(clientApi.getAddress().getZipCode(), "44-100");
        assertEquals(clientApi.getAddress().getFlatNumber(), 5);
        assertEquals(clientApi.getAddress().getHouseNumber(), 65);

        ClientApi secondClient = clientsFromDb.getBody().get(1);
        assertEquals(secondClient.getName(), "sampleName");
        assertEquals(secondClient.getSurname(), "sampleSurname");
        assertEquals(secondClient.getAddress().getCity(), "Gliwice");
        assertEquals(secondClient.getAddress().getStreet(), "Zwycięstwa");
        assertEquals(secondClient.getAddress().getZipCode(), "44-100");
        assertEquals(secondClient.getAddress().getFlatNumber(), 5);
        assertEquals(secondClient.getAddress().getHouseNumber(), 65);
    }

    @Test
    public void addClient() {
        // act
        ClientApi client = sampleClientData.getTestClientApi();
        ClientApi addedClient = testRestTemplate.postForObject(HTTP_LOCALHOST + port + "/clients", client, ClientApi.class);

        Optional<Client> clientFromDb = clientRepository.findById(addedClient.getId());

        Client clientApi = clientFromDb.get();
        // assert
        assertEquals(clientApi.getName(), "sampleName");
        assertEquals(clientApi.getSurname(), "sampleSurname");
        assertNotNull(clientApi.getAddress());
    }
}
