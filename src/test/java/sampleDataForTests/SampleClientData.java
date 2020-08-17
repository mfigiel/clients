package sampleDataForTests;

import com.clients.api.resource.AddressApi;
import com.clients.api.resource.ClientApi;
import com.clients.repository.entity.Address;
import com.clients.repository.entity.Client;

public class SampleClientData {

    private static final String NAME = "sampleName";
    private static final String SURNAME = "sampleSurname";
    private static final String CITY = "Gliwice";
    private static final String STREET = "Zwycięstwa";
    private static final String ZIP_CODE = "44-100";
    private static final int FLAT_NUMBER = 5;
    private static final int HOUSE_NUMBER = 65;

    public ClientApi getTestClientApi() {
        ClientApi clientApi = new ClientApi();
        clientApi.setName(NAME);
        clientApi.setSurname(SURNAME);
        AddressApi address = new AddressApi();
        address.setCity(CITY);
        address.setStreet(STREET);
        address.setZipCode(ZIP_CODE);
        address.setFlatNumber(FLAT_NUMBER);
        address.setHouseNumber(HOUSE_NUMBER);
        clientApi.setAddress(address);
        return clientApi;
    }

    public Client getTestClient() {
        Client client = new Client();
        client.setName(NAME);
        client.setSurname(SURNAME);
        Address address = new Address();
        address.setCity(CITY);
        address.setStreet(STREET);
        address.setZipCode(ZIP_CODE);
        address.setFlatNumber(FLAT_NUMBER);
        address.setHouseNumber(HOUSE_NUMBER);

        client.setAddress(address);
        address.setClient(client);
        return client;
    }
}
