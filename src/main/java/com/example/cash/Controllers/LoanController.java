package com.example.cash.Controllers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.example.cash.Exceptions.UserNotFoundException;
import com.example.cash.Models.Loan;
import com.example.cash.Models.User;
import com.example.cash.Repository.LoanRepository;
import com.example.cash.Repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/loans")
    public ObjectNode getAll(
            @RequestParam(required = false) Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Loan> pageLoan;
        PageRequest paging = PageRequest.of(page, size);
        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            pageLoan = loanRepository.findByUser(paging, user);
        } else {
            pageLoan = loanRepository.findAll(paging);
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        ObjectNode pag = mapper.createObjectNode();
        JsonNode loans = mapper.valueToTree(pageLoan.getContent());;
        pag.put("page", pageLoan.getNumber());
        pag.put("size", pageLoan.getSize());
        pag.put("total", pageLoan.getTotalPages());
        response.put("items", loans);
        response.put("paging", pag);

        return response;
    }
}
