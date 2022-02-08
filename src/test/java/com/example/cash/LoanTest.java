package com.example.cash;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.cash.Models.Loan;
import com.example.cash.Models.User;
import com.example.cash.Repository.LoanRepository;
import com.example.cash.Repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoanTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	LoanRepository loanRepository;
	@Autowired
	UserRepository userRepository;

	User user;

	@BeforeEach
	public void setup() {
		User u = new User();
		u.setFirstName("John");
		u.setLastName("Doe");
		u.setEmail("email@yopmail.com");
		user = userRepository.saveAndFlush(u);
	}

	@Test
	public void addLoanTest() {
		JSONObject data = new JSONObject();
		data.put("total", "1000");
		int prevLoans = loanRepository.findAll().size();
		try {
			mockMvc.perform(MockMvcRequestBuilders
					.post("/users/" + user.getId() + "/loans")
					.content(data.toString())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated())
					.andDo(x -> {
						JSONObject response = new JSONObject(x.getResponse().getContentAsString());
						assertEquals(response.get("message"), "Loan created");

						List<Loan> loans = loanRepository.findAll();
						Loan loan = loanRepository.getById((Integer) response.get("loan_id"));
						assertEquals(loans.size(), prevLoans + 1);
						assertEquals(1000, loan.getTotal().intValue());
					});
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

}