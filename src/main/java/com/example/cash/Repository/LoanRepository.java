package com.example.cash.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cash.Models.Loan;
import com.example.cash.Models.User;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Page<Loan> findAll(Pageable pageable);
    Page<Loan> findByUser(Pageable pageable, User user);
}
