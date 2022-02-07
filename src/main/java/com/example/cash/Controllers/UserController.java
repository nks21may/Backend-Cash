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

import java.util.List;

import com.example.cash.Exceptions.UserNotFoundException;
import com.example.cash.Repository.LoanRepository;
import com.example.cash.Repository.UserRepository;

@RestController
public class UserREST {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoanRepository loanRepository;

    @GetMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<String> getById(
            @PathVariable("id") Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return new ResponseEntity<String>(user.toString(), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteById(
            @PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return new ResponseEntity<String>("User deleted", HttpStatus.OK);
    }

    @PostMapping("/users")
    public User createUser(
            @RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @GetMapping("/users/{id}/loans")
    public List<Loan> getLoansByUserId(
            @PathVariable("id") Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user.getLoans();
    }

    @PostMapping(value = "/users/{id}/loans"
            , consumes = "application/json"
            , produces = "application/json"
    )
    public String createLoan(
            @PathVariable("id") Integer id,
            @RequestBody Loan loan) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        loan.setUser(user);
        loanRepository.save(loan);
        return loan.toString();
    }
}