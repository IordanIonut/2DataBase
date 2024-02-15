package com.example.DataBase.Primary.Controller;

import com.example.DataBase.Primary.Model.Reviews;
import com.example.DataBase.Primary.Service.ReviewsService;
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
@RequestMapping("/api/reviews")
public class ReviewsController {
    @Autowired
    private ReviewsService service;

    @PostMapping("/add")
    public ResponseEntity<Object> saveReviews(@Valid @RequestBody Reviews review, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(" ").append(error.getDefaultMessage()).append(";");
            }
            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        } else {
            try {
                return new ResponseEntity<>(service.saveReviews(review), HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("The date not be mandatory.", HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Reviews>> getAllReviews(){
        return new ResponseEntity<>(service.getAllReviews(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<List<Reviews>> getByIdReviews(@RequestParam Long id_review){
        return  service.getByIdReviews(id_review).map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id")
    public ResponseEntity<HttpStatus> deleteReviews(@RequestParam Long id_review) {
        service.deleteReviews(id_review);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reviews> updateReviews(@PathVariable Long id, @RequestBody Reviews review){
        Reviews r = service.updateReviews(id, review);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }
}
