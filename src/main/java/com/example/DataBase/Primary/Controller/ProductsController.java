package com.example.DataBase.Primary.Controller;

import com.example.DataBase.Primary.Model.Products;
import com.example.DataBase.Primary.Service.ProductsService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private ProductsService service;

    @PostMapping("/add")
    public ResponseEntity<Object> saveProducts(@Valid @RequestBody Products product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveProducts(product), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping()
    public ResponseEntity<List<Products>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<List<Products>> getByIdProducts(@RequestParam Long id_product) {
        return service.getByIdProducts(id_product).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteProducts(@RequestParam Long id_product) {
        service.deleteProducts(id_product);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProducts(@PathVariable Long id_product, @RequestBody Products product) {
        Products p = service.updateProduct(id_product, product);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }
}
