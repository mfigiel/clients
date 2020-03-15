package com.testing.controller;

import com.testing.api.resource.AddressApi;
import com.testing.api.resource.ClientApi;
import com.testing.repository.ClientRepository;
import com.testing.repository.entity.Address;
import com.testing.repository.entity.Client;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        Client client = getTestClient(1L);

        clientRepository.save(client);

        ClientApi clientApi = testRestTemplate.getForObject(HTTP_LOCALHOST + port + "/client/1", ClientApi.class);

        assertEquals(clientApi.getName(), "sampleName");
        assertEquals(clientApi.getSurname(), "sampleSurname");
        assertEquals(clientApi.getAddress().getCity(), "Gliwice");
        assertEquals(clientApi.getAddress().getStreet(), "Zwycięstwa");
        assertEquals(clientApi.getAddress().getZipCode(), "44-100");
        assertEquals(clientApi.getAddress().getFlatNumber(), "5");
        assertEquals(clientApi.getAddress().getHouseNumber(), "65");
    }

    @Test
    public void getClients() {
        // act

        clientRepository.save(getTestClient(2L));
        clientRepository.save(getTestClient(3L));

        ResponseEntity<List<ClientApi>> clientsFromDb = testRestTemplate.exchange(HTTP_LOCALHOST + port + "/clients",  HttpMethod.GET, null, new ParameterizedTypeReference<List<ClientApi>>() {
        });

        // assert
        assertEquals(clientsFromDb.getBody().size(), 2);
        ClientApi clientApi = clientsFromDb.getBody().get(0);
        assertEquals(clientApi.getName(), "sampleName");
        assertEquals(clientApi.getSurname(), "sampleSurname");
        assertEquals(clientApi.getAddress().getCity(), "Gliwice");
        assertEquals(clientApi.getAddress().getStreet(), "Zwycięstwa");
        assertEquals(clientApi.getAddress().getZipCode(), "44-100");
        assertEquals(clientApi.getAddress().getFlatNumber(), "5");
        assertEquals(clientApi.getAddress().getHouseNumber(), "65");

        ClientApi secondClient = clientsFromDb.getBody().get(1);
        assertEquals(secondClient.getName(), "sampleName");
        assertEquals(secondClient.getSurname(), "sampleSurname");
        assertEquals(secondClient.getAddress().getCity(), "Gliwice");
        assertEquals(secondClient.getAddress().getStreet(), "Zwycięstwa");
        assertEquals(secondClient.getAddress().getZipCode(), "44-100");
        assertEquals(secondClient.getAddress().getFlatNumber(), "5");
        assertEquals(secondClient.getAddress().getHouseNumber(), "65");
    }

    @Test
    public void addClient() {
        // act
        ClientApi client = getTestClientApi(1L);
        testRestTemplate.postForObject(HTTP_LOCALHOST + port + "/clients", client, ClientApi.class);


        Optional<Client> clientsFromDb = clientRepository.findById(1L);

        Client clientApi = clientsFromDb.get();
        // assert
        assertEquals(clientApi.getName(), "sampleName");
        assertEquals(clientApi.getSurname(), "sampleSurname");
    }

    private ClientApi getTestClientApi(Long id) {
        ClientApi clientApi = new ClientApi();
        clientApi.setName("sampleName");
        clientApi.setSurname("sampleSurname");
        AddressApi address = new AddressApi();
        address.setCity("Gliwice");
        address.setStreet("Zwycięstwa");
        address.setZipCode("44-100");
        address.setFlatNumber(5);
        address.setHouseNumber(65);

        return clientApi;
    }

    private Client getTestClient(Long id) {
        Client client = new Client();
        client.setId(id);
        client.setName("sampleName");
        client.setSurname("sampleSurname");
        Address address = new Address();
        address.setId(id);
        address.setCity("Gliwice");
        address.setStreet("Zwycięstwa");
        address.setZipCode("44-100");
        address.setFlatNumber(5);
        address.setHouseNumber(65);

        return client;
    }
}
