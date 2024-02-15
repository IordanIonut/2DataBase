package com.example.DataBase.Secondary.Service;

import com.example.DataBase.Secondary.Model.Budgets;
import com.example.DataBase.Secondary.Repository.BudgetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetsService {
    @Autowired
    private BudgetsRepository repository;

    public Budgets saveBudgets(Budgets budget){
        return repository.save(budget);
    }

    public List<Budgets> getAllBudgets(){
        return repository.findAll();
    }

    public Optional<Budgets> getByIdBudgets(Long id_budget){
        return repository.findById(id_budget);
    }

    public void deleteBudgets(Long id_budget){
        repository.deleteById(id_budget);
    }

    public Budgets updateBudgets(Long id_budget, Budgets budget){
        if(repository.existsById(id_budget)){
            budget.setId_budget(id_budget);
            return repository.save(budget);
        }
        return null;
    }

}
