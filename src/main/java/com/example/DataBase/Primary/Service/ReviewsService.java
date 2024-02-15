package com.example.DataBase.Primary.Service;

import com.example.DataBase.Primary.Model.Reviews;
import com.example.DataBase.Primary.Repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsService {
    @Autowired
    private ReviewsRepository repository;

    public Reviews saveReviews(Reviews review) {
        return repository.save(review);
    }

    public List<Reviews> getAllReviews() {
        return repository.findAll();
    }

    public Optional<Reviews> getByIdReviews(Long id_review) {
        return repository.findById(id_review);
    }

    public void deleteReviews(Long id_review) {
        repository.deleteById(id_review);
    }

    public Reviews updateReviews(Long id_review, Reviews review) {
        if (repository.existsById(id_review)) {
            review.setId_review(id_review);
            return repository.save(review);
        }
        return null;
    }
}
