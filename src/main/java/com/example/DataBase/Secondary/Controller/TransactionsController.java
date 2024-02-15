package com.example.DataBase.Secondary.Controller;

import com.example.DataBase.Secondary.Model.Transactions;
import com.example.DataBase.Secondary.Service.TransactionsService;
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
@RequestMapping("/api/transactions")
public class TransactionsController {
    @Autowired
    private TransactionsService service;

    @PostMapping("/add")
    public ResponseEntity<Object> saveTransactions(@Valid @RequestBody Transactions transactions, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveTransactions(transactions), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping()
    public ResponseEntity<List<Transactions>> getAllTransactions(){
        return new ResponseEntity<>(service.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<List<Transactions>> getByIdTransactions(@RequestParam Long id_transactions){
        return service.getByIdTransactions(id_transactions).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteTransactions(@RequestParam Long id_transactions){
        service.deleteTransactions(id_transactions);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transactions> updateTransactions(@PathVariable Long id_transactions, @RequestBody Transactions transaction){
        Transactions t = service.updateTransactions(id_transactions, transaction);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }
}
