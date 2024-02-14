package com.example.DataBase.Secondary.Repository;

import com.example.DataBase.Secondary.Model.FinancialReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialReportsRepository extends JpaRepository<FinancialReports, Long> {
}