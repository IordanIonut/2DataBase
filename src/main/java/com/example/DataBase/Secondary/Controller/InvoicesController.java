package com.example.DataBase.Secondary.Controller;

import com.example.DataBase.Secondary.Model.Invoices;
import com.example.DataBase.Secondary.Service.InvoicesService;
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
@RequestMapping("/api/invoices")
public class InvoicesController {
    @Autowired
    private InvoicesService service;

    @PostMapping("/add")
    public ResponseEntity<Object> saveInvoices(@Valid @RequestBody Invoices invoice, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveInvoices(invoice), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Invoices>> getAllInvoices() {
        return new ResponseEntity<>(service.getAllInvoices(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<List<Invoices>> getInvoicesById(@RequestParam Long id_invoice){
        return service.getByIdInvoices(id_invoice).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteInvoices(@RequestParam Long id_invoices){
        service.deleteInvoices(id_invoices);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoices> updateInvoices(@PathVariable Long id_invoices, @RequestBody Invoices invoice){
        Invoices i = service.updateInvoices(id_invoices, invoice);
        return i != null ? ResponseEntity.ok(i) : ResponseEntity.notFound().build();
    }
}
