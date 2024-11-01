package kg.eventBish.service;

import kg.eventBish.model.Client;
import java.util.List;

public interface IClientService {
    public void createClient(Client client);
    public List<Client> listClients();
    public Client findClient(Long clientId);
    public void deleteClient(Long clientId);
    public Client editClient(Long clientId, Long newClientId, String newName, String newLastName, Long newDni);
}
