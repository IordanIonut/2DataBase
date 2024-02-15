package com.example.DataBase.Secondary.Service;

import com.example.DataBase.Secondary.Model.FinancialReports;
import com.example.DataBase.Secondary.Repository.FinancialReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialReportsService {
    @Autowired
    private FinancialReportsRepository repository;

    public FinancialReports saveFinancialReports(FinancialReports financialReport) {
        return repository.save(financialReport);
    }

    public List<FinancialReports> getAllFinancialReports() {
        return repository.findAll();
    }

    public Optional<FinancialReports> getByIdFianancialReports(Long id_financialReport) {
        return repository.findById(id_financialReport);
    }

    public void deleteFinancialReports(Long id_financialReport) {
        repository.deleteById(id_financialReport);
    }

    public FinancialReports updateFinancialReports(Long id_financialReport, FinancialReports financialReport) {
        if (repository.existsById(id_financialReport)) {
            financialReport.setId_report(id_financialReport);
            return repository.save(financialReport);
        }
        return null;
    }

}

