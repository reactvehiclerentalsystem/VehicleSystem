package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.GuestUser;
import com.capgemini.entities.Queries;
import com.capgemini.entities.Vehicle;
import com.capgemini.exception.ListIsEmptyException;
import com.capgemini.repository.GuestUserRepository;
import com.capgemini.repository.QueriesRepository;
import com.capgemini.repository.VehicleRepository;

@RestController
@RequestMapping("/api/guest")
public class GuestUserController {
	
	@Autowired
	private GuestUserRepository guestUserRepository;

	@Autowired
	private QueriesRepository queriesRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@GetMapping("/search/type/{vehicleType}")
	public ResponseEntity<List<Vehicle>> searchVehicleByType(@PathVariable String vehicleType)
			throws ListIsEmptyException {
		List<Vehicle> vehicle = vehicleRepository.findByVehicleType(vehicleType);
		if (vehicle != null) {
			return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("Vehicle type is not present.");
		}
	}

	@GetMapping("/search/location/{vehicleLocation}")
	public ResponseEntity<List<Vehicle>> searchVehicleByLocation(@PathVariable String vehicleLocation)
			throws ListIsEmptyException {
		List<Vehicle> vehicle = vehicleRepository.findByVehicleLocation(vehicleLocation);
		if (vehicle != null) {
			return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("Vehicle location is not present.");
		}
	}

	@GetMapping("/search/seatCapacity/{numberOfSeats}")
	public ResponseEntity<List<Vehicle>> searchVehicleBySeatCapacity(@PathVariable int numberOfSeats)
			throws ListIsEmptyException {
		List<Vehicle> vehicle = vehicleRepository.findByNumberOfSeats(numberOfSeats);
		if (vehicle != null) {
			return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("Vehicle according to prefered seat not present.");
		}
	}

	@GetMapping("/search/type/{vehicleType}/name/{vehicleName}")
	public ResponseEntity<List<Vehicle>> searchVehicle(@PathVariable String vehicleType,
			@PathVariable String vehicleName) throws ListIsEmptyException {
		List<Vehicle> vehicle = vehicleRepository.findByVehicleTypeAndVehicleName(vehicleType, vehicleName);
		if (vehicle != null) {
			return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("Vehicle is not present.");
		}
	}

	@GetMapping("/search/type/{vehicleType}/name/{vehicleName}/color/{vehicleColor}")
	public ResponseEntity<List<Vehicle>> seacrhVehicleV1(@PathVariable String vehicleType,
			@PathVariable String vehicleName, @PathVariable String vehicleColor) throws ListIsEmptyException {
		List<Vehicle> vehicle = vehicleRepository.findByVehicleTypeAndVehicleNameAndVehicleColor(vehicleType,
				vehicleName, vehicleColor);
		if (vehicle != null) {
			return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("Vehicle is not present.");
		}
	}

	@GetMapping("/search/allVehicles")
	public ResponseEntity<List<Vehicle>> searchAllVehicle() throws ListIsEmptyException {
		List<Vehicle> vehicle = vehicleRepository.findAll();
		if (vehicle != null) {
			return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
		}else {
			throw new ListIsEmptyException("No vehicle is registered.");
		}
	}


	@PostMapping("/post/{query}")
	public String postQuery(@PathVariable String query) {
		Queries queries = new Queries();
		queries.setQuery(query);
		queriesRepository.save(queries);
		return "Query Posted";
	}
	
	@PostMapping("/create")
	public String create(@RequestBody GuestUser user) {
		guestUserRepository.save(user);
		
		return "Guset User Created";
		
	}
}
