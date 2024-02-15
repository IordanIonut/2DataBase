package com.example.DataBase.Secondary.Service;

import com.example.DataBase.Secondary.Model.Invoices;
import com.example.DataBase.Secondary.Repository.InvoicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoicesService {
    @Autowired
    private InvoicesRepository repository;

    public Invoices saveInvoices(Invoices invoice) {
        return repository.save(invoice);
    }

    public List<Invoices> getAllInvoices() {
        return repository.findAll();
    }

    public Optional<Invoices> getByIdInvoices(Long id) {
        return repository.findById(id);
    }

    public void deleteInvoices(Long id_invoice) {
        repository.deleteById(id_invoice);
    }

    public Invoices updateInvoices(Long id_invoice, Invoices invoice) {
        if (repository.existsById(id_invoice)) {
            invoice.setId_invoice(id_invoice);
            return repository.save(invoice);
        }
        return null;
    }
}
