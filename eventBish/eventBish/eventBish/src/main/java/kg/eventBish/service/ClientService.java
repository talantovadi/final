package kg.eventBish.service;

import kg.eventBish.model.Client;
import kg.eventBish.repository.IClientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientService {
    @Autowired
    private IClientRepository clientRepository;

    @Override
    public void createClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client findClient(Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    public Client editClient(Long clientId, Long newClientId, String newName, String newLastName, Long newDni) {
        Client c = clientRepository.findById(clientId).orElse(null);
        if (newClientId != null) {
            c.setClientId(clientId);
        }
        if (newName != null) {
            c.setFirstName(newName);
        }
        if (newLastName != null) {
            c.setLastName(newLastName);
        }
        if (newDni != null) {
            c.setDni(newDni);
        }
        clientRepository.save(c);
        return c;
    }
}
