package com.capgemini.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capgemini.entities.ContactUs;
import com.capgemini.repository.ContactUsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ContactUsController.class)
class ContactUsControllerTest {
	@Autowired
	private MockMvc mockMvc; //It encapsulates all web application beans and makes them available for testing.
	@MockBean  //It allow to mock a class or an interface and to record and verify behaviors on it.
	ContactUsRepository contactUsRepository;
	
	@Test
	void testAddContactUs() throws Exception {
		ContactUs contactUs=new ContactUs();
		contactUs.setEmail("shubham@gmail.com");
		contactUs.setMobile("1234567890");
		mockMvc.perform(post("/api/contactus/addcontact/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(contactUs))
				)
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
