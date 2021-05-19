package com.capgemini.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capgemini.entities.Admin;
import com.capgemini.repository.AdminRepository;
import com.capgemini.repository.ContactUsRepository;
import com.capgemini.repository.QueriesRepository;
import com.capgemini.repository.TestimonialRepository;
import com.capgemini.repository.UserInfoRepository;
import com.capgemini.repository.VehicleBookingRepository;
import com.capgemini.repository.VehicleBrandRepository;
import com.capgemini.repository.VehicleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AdminController.class)
class AdminControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	AdminRepository adminRepository;
	
	@MockBean
	VehicleRepository  vehicleRepository;
	
	@MockBean
	VehicleBookingRepository vehicleBookingRepository;

	@MockBean
	VehicleBrandRepository vehicleBrandRepository;

	@MockBean
	ContactUsRepository contactUsRepo;

	@MockBean
	QueriesRepository queriesRepository;

	@MockBean
	UserInfoRepository userInfoRepository;

	@MockBean
	TestimonialRepository testimonialRepository;

	@Test
	void testCreateAdmin() throws Exception {
		Admin admin = new Admin();
		admin.setAdmin_id(1);
		admin.setEmail("bharath007@gmail.com");
		admin.setPassword("Bharath");
		mockMvc.perform(post("/api/admin/createadmin/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(admin))
				)
		.andExpect(MockMvcResultMatchers.content().string(containsString("Record Added:")));

		
	}

}
