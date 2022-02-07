package com.example.cash.Controllers;

import java.util.List;

import com.example.cash.Models.Loan;
import com.example.cash.Repository.LoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanREST {
    @Autowired
    LoanRepository loanRepository;

    @GetMapping("/loans")
    public List<Loan> todos(){
        return loanRepository.findAll();
    }
}
