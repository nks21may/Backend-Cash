package com.example.cash.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cash.Models.Loan;
import com.example.cash.Models.User;

import java.math.BigDecimal;
import java.util.List;

import com.example.cash.Exceptions.UserNotFoundException;
import com.example.cash.Repository.LoanRepository;
import com.example.cash.Repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoanRepository loanRepository;

    @GetMapping(value = "/users/{id}", produces = "application/json")
    public User getById(
            @PathVariable("id") Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteById(
            @PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return new ResponseEntity<String>("User deleted", HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(
            @RequestBody User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return new ResponseEntity<String>("User already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<String>("User created", HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}/loans")
    public List<Loan> getLoansByUserId(
            @PathVariable("id") Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getLoans();
    }

    @PostMapping(value = "/users/{id}/loans", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createLoan(
            @PathVariable("id") Integer id,
            @RequestBody Loan loan) {
        if (loan.getTotal().compareTo(new BigDecimal(0)) <= 0) {
            return new ResponseEntity<String>("Loan total must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        loan.setUser(user);
        loanRepository.save(loan);
        return new ResponseEntity<String>("Loan created", HttpStatus.CREATED);
    }
}