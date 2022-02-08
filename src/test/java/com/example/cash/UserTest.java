package com.example.cash;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.cash.Models.User;
import com.example.cash.Repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;

	@Test
	public void createUserTest() {
		JSONObject data = new JSONObject();
		data.put("firstName", "John");
		data.put("lastName", "Doe");
		data.put("email", "test@tes.com");
		try {
			int cantUsers = userRepository.findAll().size();
			this.mockMvc
					.perform(MockMvcRequestBuilders
							.post("/users")
							.content(data.toString())
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated())
					.andDo(x -> {
						assertEquals(x.getResponse().getContentAsString(), "User created");
						assertEquals(userRepository.findAll().size(), cantUsers + 1);
					});
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void checkUniqueEmail() {
		JSONObject data = new JSONObject();
		data.put("firstName", "John");
		data.put("lastName", "Doe");
		data.put("email", "test@tes.com");
		try {
			User u = new User(data.getString("email"), data.getString("firstName"), data.getString("lastName"));
			User u2 = new User(data.getString("email"), data.getString("firstName") + "2",
					data.getString("lastName") + "2");
			userRepository.saveAndFlush(u);
			userRepository.saveAndFlush(u2);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("ConstraintViolationException"));
		}
	}
}