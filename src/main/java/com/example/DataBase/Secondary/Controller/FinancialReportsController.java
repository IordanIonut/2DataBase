package com.example.DataBase.Secondary.Controller;

import com.example.DataBase.Primary.Model.Reviews;
import com.example.DataBase.Secondary.Model.FinancialReports;
import com.example.DataBase.Secondary.Service.FinancialReportsService;
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
@RequestMapping("/api/financial/reports")
public class FinancialReportsController {
    @Autowired
    private FinancialReportsService service;

    @PostMapping("/add")
    public ResponseEntity<Object> saveFinancialReports(@Valid @RequestBody FinancialReports financialReport, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveFinancialReports(financialReport), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping()
    public ResponseEntity<List<FinancialReports>> getAllFinancialReports(){
        return new ResponseEntity<>(service.getAllFinancialReports(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<List<FinancialReports>> getByIdFinancialReports(@RequestParam("id") Long id_financialReport){
        return service.getByIdFianancialReports(id_financialReport).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteFinancialReports(@RequestParam("id") Long id_FinancialReport){
        service.deleteFinancialReports(id_FinancialReport);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialReports> updateFinancialReports(@PathVariable("id") Long id_FinancialReport, @RequestBody FinancialReports financialReport){
        FinancialReports f = service.updateFinancialReports(id_FinancialReport, financialReport);
        return f != null ? ResponseEntity.ok(f) : ResponseEntity.notFound().build();
    }
}
