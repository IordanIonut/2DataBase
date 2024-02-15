package com.example.DataBase.Secondary.Service;

import com.example.DataBase.Secondary.Model.Transactions;
import com.example.DataBase.Secondary.Repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionsService {
    @Autowired
    private TransactionsRepository repository;

    public Transactions saveTransactions(Transactions transaction) {
        return repository.save(transaction);
    }

    public List<Transactions> getAllTransactions() {
        return repository.findAll();
    }

    public Optional<Transactions> getByIdTransactions(Long id_transaction) {
        return repository.findById(id_transaction);
    }

    public void deleteTransactions(Long id_transaction) {
        repository.deleteById(id_transaction);
    }

    public Transactions updateTransactions(Long id_transaction, Transactions transaction) {
        if (repository.existsById(id_transaction)) {
            transaction.setId_transaction(id_transaction);
            return repository.save(transaction);
        }
        return null;
    }
}
