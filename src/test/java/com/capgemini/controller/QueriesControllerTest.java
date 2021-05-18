package com.capgemini.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capgemini.repository.QueriesRepository;

@WebMvcTest(QueriesController.class)
class QueriesControllerTest {
	@Autowired
	private MockMvc mockMvc;    //It encapsulates all web application beans and makes them available for testing
	@MockBean
	QueriesRepository queriesRepo;
	
	@Test
	void testPostQuery() throws Exception {
		mockMvc.perform(post("/api/queries/post/hello,it's not working"))
		.andExpect(MockMvcResultMatchers.content().string(containsString("Query Posted")));
	}

}
