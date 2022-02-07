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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserTest {

    @Autowired
	private MockMvc mockMvc;

    @Test
    public void createUserTest() {
        JSONObject data = new JSONObject();
        data.put("firstName", "John");
        data.put("lastName", "Doe");
        data.put("email", "test@tes.com");
        
        try {
			this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/users")
						.content(data.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(x -> {
					JSONObject response = new JSONObject(x.getResponse().getContentAsString());
					response.remove("id");
					response.remove("loans");
					assertEquals(data.toString(), response.toString());
				});
			
		} catch (Exception e) {
			e.printStackTrace();
            fail();
		}
    }
}