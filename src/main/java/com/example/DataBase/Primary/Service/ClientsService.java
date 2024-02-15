package com.example.DataBase.Primary.Service;

import com.example.DataBase.Primary.Model.Clients;
import com.example.DataBase.Primary.Repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {
    @Autowired
    private ClientsRepository repository;

    public Clients saveClients(Clients client) {
        return repository.save(client);
    }

    public List<Clients> getAllClients() {
        return repository.findAll();
    }

    public Optional<Clients> getByIdClients(Long id_client) {
        return repository.findById(id_client);
    }

    public void deleteClients(Long id_client) {
        repository.deleteById(id_client);
    }

    public Clients updateClients(Long id_client, Clients client) {
        if (repository.existsById(id_client)) {
            client.setId_client(id_client);
            return repository.save(client);
        }
        return null;
    }
}
