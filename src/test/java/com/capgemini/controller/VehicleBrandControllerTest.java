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

import com.capgemini.entities.VehicleBrand;
import com.capgemini.repository.VehicleBrandRepository;
import com.capgemini.repository.VehicleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VehicleBrandController.class)
class VehicleBrandControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	VehicleBrandRepository vehicleBrandRepository;
	@MockBean
	VehicleRepository vehicleRepository;

	@Test
	void testCreateBrand() throws Exception {
		VehicleBrand vb = new VehicleBrand();
		vb.setBrand_name("Maruti");
		mockMvc.perform(post("/api/brand/create/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(vb)))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	

}
