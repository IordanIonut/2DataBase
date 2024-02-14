package com.example.DataBase.Secondary.Repository;

import com.example.DataBase.Secondary.Model.Budgets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetsRepository extends JpaRepository<Budgets, Long> {
}