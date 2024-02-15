package com.example.DataBase.Primary.Controller;

import com.example.DataBase.Primary.Model.Clients;
import com.example.DataBase.Primary.Model.Reviews;
import com.example.DataBase.Primary.Service.ClientsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/clients")
public class ClientsController {
    @Autowired
    private ClientsService service;

    @PostMapping("/add")
    public ResponseEntity<Object> saveReviews(@Valid @RequestBody Clients client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveClients(client), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping()
    public ResponseEntity<List<Clients>> getAllClients() {
        return new ResponseEntity<>(service.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<List<Clients>> getByIdClients(@RequestParam Long id_client) {
        return service.getByIdClients(id_client).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteClients(@RequestParam Long id_client) {
        service.deleteClients(id_client);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clients> updateClients(@PathVariable Long id_client, @RequestBody Clients client) {
        Clients c = service.updateClients(id_client, client);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }
}
