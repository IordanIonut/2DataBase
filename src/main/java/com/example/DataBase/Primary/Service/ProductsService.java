package com.example.DataBase.Primary.Service;

import com.example.DataBase.Primary.Model.Products;
import com.example.DataBase.Primary.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository repository;

    public Products saveProducts(Products product) {
        return repository.save(product);
    }

    public List<Products> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Products> getByIdProducts(Long id_product) {
        return repository.findById(id_product);
    }

    public void deleteProducts(Long id_product) {
        repository.deleteById(id_product);
    }

    public Products updateProduct(Long id_product, Products product) {
        if (repository.existsById(id_product)) {
            product.setId_product(id_product);
            return repository.save(product);
        }
        return null;
    }
}
