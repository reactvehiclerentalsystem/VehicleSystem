package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Queries;
import com.capgemini.entities.Vehicle;
import com.capgemini.repository.QueriesRepository;
import com.capgemini.repository.VehicleRepository;

@RestController
@RequestMapping("/api/guest")
public class GuestUserController {

	@Autowired
	private QueriesRepository queriesRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@GetMapping("/search/name/{vehicleName}")
	public ResponseEntity<List<Vehicle>> searchVehicleByName(@PathVariable String vehicleName) {
		List<Vehicle> vehicle =  vehicleRepository.findByVehicleName(vehicleName);
		return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/type/{vehicleType}")
	public ResponseEntity <List<Vehicle>> searchVehicleByType(@PathVariable String vehicleType) {
	    List<Vehicle> vehicle = vehicleRepository.findByVehicleType(vehicleType);
		return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/location/{vehicleLocation}")
	public ResponseEntity<List<Vehicle>> searchVehicleByLocation(@PathVariable String vehicleLocation) {
		List<Vehicle> vehicle = vehicleRepository.findByVehicleLocation(vehicleLocation);
		return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/seatCapacity/{numberOfSeats}")
	public ResponseEntity<List<Vehicle>> searchVehicleBySeatCapacity(@PathVariable int numberOfSeats) {
		 List<Vehicle> vehicle = vehicleRepository.findByNumberOfSeats(numberOfSeats);
		return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/type/{vehicleType}/name/{vehicleName}")
	public ResponseEntity<List<Vehicle>> searchVehicle(@PathVariable String vehicleType, @PathVariable String vehicleName) {
		List<Vehicle> vehicle = vehicleRepository.findByVehicleTypeAndVehicleName(vehicleType, vehicleName);
		return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/type/{vehicleType}/name/{vehicleName}/color/{vehicleColor}")
	public ResponseEntity<List<Vehicle>> seacrhVehicleV1(@PathVariable String vehicleType, @PathVariable String vehicleName,
			@PathVariable String vehicleColor) {
		List<Vehicle> vehicle = vehicleRepository.findByVehicleTypeAndVehicleNameAndVehicleColor(vehicleType, vehicleName,
				vehicleColor);
		return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/all")
	public ResponseEntity<List<Vehicle>> searchAllVehicle() {
		List<Vehicle> vehicle = vehicleRepository.findAll();
		return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
	}

	@PostMapping("/post/{query}")
	public String postQuery(@PathVariable String query) {
		Queries queries = new Queries();
		queries.setQuery(query);
		queriesRepository.save(queries);
		return "Query Posted";

	}
}
