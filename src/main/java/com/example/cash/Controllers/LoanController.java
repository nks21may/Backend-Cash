package com.example.cash.Controllers;

import java.util.stream.Collectors;

import com.example.cash.Exceptions.UserNotFoundException;
import com.example.cash.Models.Loan;
import com.example.cash.Models.User;
import com.example.cash.Repository.LoanRepository;
import com.example.cash.Repository.UserRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/loans", produces = "application/json")
    public String getAll(
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

        String loans = pageLoan.getContent()
                .stream()
                .map(Loan::toString)
                .collect(Collectors.joining(", ", "[", "]"));
        JSONObject response = new JSONObject();
        JSONObject pag = new JSONObject();
        JSONArray lo = new JSONArray(loans);
        pag.put("total", pageLoan.getTotalElements());
        pag.put("size", pageLoan.getSize());
        pag.put("page", pageLoan.getNumber());
        response.put("items", lo);
        response.put("paging", pag);

        return response.toString();
    }
}
