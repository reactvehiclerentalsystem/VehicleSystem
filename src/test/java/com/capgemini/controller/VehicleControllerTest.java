package com.capgemini.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capgemini.entities.Vehicle;
import com.capgemini.entities.VehicleBrand;
import com.capgemini.repository.VehicleBrandRepository;
import com.capgemini.repository.VehicleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	VehicleRepository vehicleRepository;
	@MockBean
	VehicleBrandRepository vehicleBrandRepository;

	@Test
	void testsearchVehicleByName() throws Exception {
		VehicleBrand vb = new VehicleBrand();
		vb.setBrand_id(1);
		vb.setBrand_name("Maruti");
		vb.setDeleted(false);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehiclePlateNumber("BR1234");
		vehicle.setVehicleName("swift");
		vehicle.setVehicleType("sports");
		vehicle.setVehicleColor("red");
		vehicle.setVehicleLocation("patna");
		vehicle.setNumberOfSeats(4);
		vehicle.setDailyPrice(1500);
		vehicle.setAvailable(true);
		vehicle.setDeleted(false);
		vehicle.setVehicleBrand(vb);

		mockMvc.perform(get("/api/vehicle/search/name/{vehicleName}", "swift"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testSearchVehicleByType() throws Exception {
		VehicleBrand vb = new VehicleBrand();
		vb.setBrand_id(1);
		vb.setBrand_name("Maruti");
		vb.setDeleted(false);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehiclePlateNumber("BR1234");
		vehicle.setVehicleName("swift");
		vehicle.setVehicleType("sports");
		vehicle.setVehicleColor("red");
		vehicle.setVehicleLocation("patna");
		vehicle.setNumberOfSeats(4);
		vehicle.setDailyPrice(1500);
		vehicle.setAvailable(true);
		vehicle.setDeleted(false);
		vehicle.setVehicleBrand(vb);

		mockMvc.perform(get("/api/vehicle/search/type/{vehicleType}", "sports"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testSearchVehicleByLocation() throws Exception {
		VehicleBrand vb = new VehicleBrand();
		vb.setBrand_id(1);
		vb.setBrand_name("Maruti");
		vb.setDeleted(false);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehiclePlateNumber("BR1234");
		vehicle.setVehicleName("swift");
		vehicle.setVehicleType("sports");
		vehicle.setVehicleColor("red");
		vehicle.setVehicleLocation("patna");
		vehicle.setNumberOfSeats(4);
		vehicle.setDailyPrice(1500);
		vehicle.setAvailable(true);
		vehicle.setDeleted(false);
		vehicle.setVehicleBrand(vb);

		mockMvc.perform(get("/api/vehicle/search/location/{vehicleLocation}", "patna"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testSearchVehicleBySeatCapacity() throws Exception {
		VehicleBrand vb = new VehicleBrand();
		vb.setBrand_id(1);
		vb.setBrand_name("Maruti");
		vb.setDeleted(false);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehiclePlateNumber("BR1234");
		vehicle.setVehicleName("swift");
		vehicle.setVehicleType("sports");
		vehicle.setVehicleColor("red");
		vehicle.setVehicleLocation("patna");
		vehicle.setNumberOfSeats(4);
		vehicle.setDailyPrice(1500);
		vehicle.setAvailable(true);
		vehicle.setDeleted(false);
		vehicle.setVehicleBrand(vb);

		mockMvc.perform(get("/api/vehicle/search/seatCapacity/{numberOfSeats}", 4))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testSearchVehicle() throws Exception {
		VehicleBrand vb = new VehicleBrand();
		vb.setBrand_id(1);
		vb.setBrand_name("Maruti");
		vb.setDeleted(false);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehiclePlateNumber("BR1234");
		vehicle.setVehicleName("swift");
		vehicle.setVehicleType("sports");
		vehicle.setVehicleColor("red");
		vehicle.setVehicleLocation("patna");
		vehicle.setNumberOfSeats(4);
		vehicle.setDailyPrice(1500);
		vehicle.setAvailable(true);
		vehicle.setDeleted(false);
		vehicle.setVehicleBrand(vb);

		mockMvc.perform(get("/api/vehicle/search/type/{vehicleType}/name/{vehicleName}", "sports", "swift"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testSeacrhVehicleV1() throws Exception {
		VehicleBrand vb = new VehicleBrand();
		vb.setBrand_id(1);
		vb.setBrand_name("Maruti");
		vb.setDeleted(false);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehiclePlateNumber("BR1234");
		vehicle.setVehicleName("swift");
		vehicle.setVehicleType("sports");
		vehicle.setVehicleColor("red");
		vehicle.setVehicleLocation("patna");
		vehicle.setNumberOfSeats(4);
		vehicle.setDailyPrice(1500);
		vehicle.setAvailable(true);
		vehicle.setDeleted(false);
		vehicle.setVehicleBrand(vb);

		mockMvc.perform(get("/api/vehicle/search/type/{vehicleType}/name/{vehicleName}/color/{vehicleColor}", "sports",
				"swift", "red")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testSearchAllVehicle() throws Exception {
		VehicleBrand vb = new VehicleBrand();
		vb.setBrand_id(1);
		vb.setBrand_name("Maruti");
		vb.setDeleted(false);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehiclePlateNumber("BR1234");
		vehicle.setVehicleName("swift");
		vehicle.setVehicleType("sports");
		vehicle.setVehicleColor("red");
		vehicle.setVehicleLocation("patna");
		vehicle.setNumberOfSeats(4);
		vehicle.setDailyPrice(1500);
		vehicle.setAvailable(true);
		vehicle.setDeleted(false);
		vehicle.setVehicleBrand(vb);

		mockMvc.perform(get("/api/vehicle/search/allVehicles")).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
