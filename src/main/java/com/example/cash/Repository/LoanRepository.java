package com.example.cash.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cash.Models.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

}
