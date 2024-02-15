package com.example.DataBase.Secondary.Controller;

import com.example.DataBase.Primary.Model.Reviews;
import com.example.DataBase.Secondary.Model.Budgets;
import com.example.DataBase.Secondary.Service.BudgetsService;
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
@RequestMapping("/api/budgets")
public class BudgetsController {
    @Autowired
    private BudgetsService service;

    @PostMapping("/add")
    public ResponseEntity<Object> saveBudgets(@Valid @RequestBody Budgets budget, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveBudgets(budget), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Budgets>> getAllBudgets(){
        return new ResponseEntity<>(service.getAllBudgets(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<List<Budgets>> getByIdBudgets(@RequestParam Long id_review){
        return  service.getByIdBudgets(id_review).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteBudgets(@RequestParam Long id_budget) {
        service.deleteBudgets(id_budget);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budgets> updateBudgets(@PathVariable Long id, @RequestBody Budgets budget){
        Budgets b = service.updateBudgets(id, budget);
        return b != null ? ResponseEntity.ok(b) : ResponseEntity.notFound().build();
    }
}
