package com.example.DataBase.Primary.Controller;

import com.example.DataBase.Primary.Model.Orders;
import com.example.DataBase.Primary.Model.Reviews;
import com.example.DataBase.Primary.Service.OrdersService;
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
@RequestMapping("/api/orders")
public class OrdersController {
    @Autowired
    private OrdersService service;

    @PostMapping("/add")
    public ResponseEntity<Object> saveOrders(@Valid @RequestBody Orders order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveOrders(order), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping()
    public ResponseEntity<List<Orders>> getAllOrders() {
        return new ResponseEntity<>(service.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<List<Orders>> getByIdOrders(@RequestParam Long id_order) {
        return service.getByIdOrders(id_order).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteOrders(@RequestParam Long id_order) {
        service.deleteOrders(id_order);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable Long id, @RequestBody Orders order) {
        Orders o = service.updateOrders(id, order);
        return o != null ? ResponseEntity.ok(o) : ResponseEntity.notFound().build();
    }
}
