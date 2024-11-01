package kg.eventBish.controller;

import kg.eventBish.model.Client;
import kg.eventBish.service.IClientService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private IClientService clientService;

    @PostMapping("/clients/create")
    public String createClient(@RequestBody Client client){
        clientService.createClient(client);
        return "Client created successfully";
    }

    @GetMapping("/clients")
    public List<Client> listClients(){
        return clientService.listClients();
    }

    @GetMapping("/clients/{clientId}")
    public Client getClient(@PathVariable Long clientId){
        return clientService.findClient(clientId);
    }

    @DeleteMapping("/clients/delete/{clientId}")
    public String deleteClient(@PathVariable Long clientId){
        clientService.deleteClient(clientId);
        return "Client deleted successfully";
    }

    @PutMapping("/clients/edit/{clientId}")
    public Client editClient(@PathVariable Long clientId,
                             @RequestParam(required = false, name="newClientId") Long newClientId,
                             @RequestParam(required = false, name="newFirstName") String newFirstName,
                             @RequestParam(required = false, name="newLastName") String newLastName,
                             @RequestParam(required = false, name="newDni") Long newDni){

        return clientService.editClient(clientId, newClientId, newFirstName, newLastName, newDni);
    }
}
