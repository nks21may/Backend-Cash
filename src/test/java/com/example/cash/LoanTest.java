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

import java.math.BigDecimal;
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
						assertEquals(x.getResponse().getContentAsString(), "Loan created");
						List<Loan> loans = loanRepository.findAll();
						assertEquals(loans.size(), prevLoans + 1);
					});
			return;
		} catch (Exception e) {
			System.out.println("++++++++++++++++++++++");
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			fail();
		}
		assertEquals(loanRepository.findAll().size(), 1);
	}

}