package com.capgemini.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capgemini.entities.UserInfo;
import com.capgemini.repository.TestimonialRepository;
import com.capgemini.repository.UserInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserInfoController.class)
class UserInfoControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	UserInfoRepository userInfoRepository;
	@MockBean
	TestimonialRepository testimonialRepository;

	@Test
	void testCreate() throws Exception {
		UserInfo user = new UserInfo();
		user.setUserName("Shubham");
		user.setUserEmail("shubham@gmail.com");
		user.setUserNumber("1234567890");
		user.setUserPassword("123@abc");
		user.setUserAdhaar("1234567890");
		user.setDeleted(false);
		mockMvc.perform(post("/api/info/").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testFindById() throws Exception {
		UserInfo user = new UserInfo();
		user.setUserId(1);
		user.setUserName("Shubham");
		user.setUserEmail("shubham@gmail.com");
		user.setUserNumber("1234567890");
		user.setUserPassword("123@abc");
		user.setUserAdhaar("1234567890");
		user.setDeleted(false);

		Mockito.when(userInfoRepository.existsById(Mockito.anyInt())).thenReturn(true);
		Mockito.when(userInfoRepository.findById(Mockito.anyInt())).thenReturn(Mockito.any());
		mockMvc.perform(get("/api/info/user/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testPostTestimonial() throws Exception {
		mockMvc.perform(post("/api/info/post/testimonial/It was great experience")).andExpect(
				MockMvcResultMatchers.content().string(containsString("Testimonial posted by Registered user")));
	}
}
