package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Vehicle;
import com.capgemini.entities.VehicleBrand;
//import com.capgemini.entities.VehicleBooking;
import com.capgemini.exception.VehicleIdNotFoundException;
import com.capgemini.repository.VehicleBrandRepository;
import com.capgemini.repository.VehicleRepository;

@RestController
@RequestMapping("/api/vehicle/")
public class VehicleController {
	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	VehicleBrandRepository vehicleBrandRepository;

	@PostMapping("/add/{brand_id}")
	public String addVehicle(@RequestBody Vehicle vehicle, @PathVariable int brand_id) {

		VehicleBrand vehicleBrand = vehicleBrandRepository.findById(brand_id).get();
		vehicle.setVehicleBrand(vehicleBrand);

		vehicleRepository.save(vehicle);
		return "Vehicle Added";
	}

	@PutMapping("/update/{vehicleId}")
	public String updateVehicle(@RequestBody Vehicle vehicle, @PathVariable int vehicleId)
			throws VehicleIdNotFoundException {
		Vehicle vehicle1 = vehicleRepository.findById(vehicleId).get();
		if (vehicle1 != null) {
			vehicleRepository.save(vehicle1);// if vehicle is is present it will get
			// deleted , hence cancelled. return "Vehicle data updated!";
			return "Vehicle Updated!";
		} else {

			throw new VehicleIdNotFoundException("Incorrect Id! Enter correct Id!");
		}

	}

	@PutMapping("/delete/{vehicleId}")
	public String removeVehicle(@PathVariable int vehicleId) throws VehicleIdNotFoundException {

		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();

		if (vehicle != null && vehicle.isDeleted() == false) // if vehicle id is present and vehicle status is not
																// deleted then the method will get accessed.
		{

			vehicle.setDeleted(true);
			vehicleRepository.save(vehicle);// if vehicle is is present it will get deleted , hence
											// cancelled.
			return "Booking Cancelled!";
		} else {
			throw new VehicleIdNotFoundException("Incorrect Id! Enter correct Id!");
		}

	}

	@GetMapping("/search/name/{vehicleName}")
	public ResponseEntity<Vehicle> searchVehicleByName(@PathVariable String vehicleName) {
		Vehicle vehicle = vehicleRepository.findByVehicleName(vehicleName);
		return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/type/{vehicleType}")
	public ResponseEntity<Vehicle> searchVehicleByType(@PathVariable String vehicleType) {
		Vehicle vehicle = vehicleRepository.findByVehicleType(vehicleType);
		return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/location/{vehicleLocation}")
	public ResponseEntity<Vehicle> searchVehicleByLocation(@PathVariable String vehicleLocation) {
		Vehicle vehicle = vehicleRepository.findByVehicleLocation(vehicleLocation);
		return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/seatCapacity/{numberOfSeats}")
	public ResponseEntity<Vehicle> searchVehicleBySeatCapacity(@PathVariable int numberOfSeats) {
		Vehicle vehicle = vehicleRepository.findByNumberOfSeats(numberOfSeats);
		return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/type/{vehicleType}/name/{vehicleName}")
	public ResponseEntity<Vehicle> searchVehicle(@PathVariable String vehicleType, @PathVariable String vehicleName) {
		Vehicle vehicle = vehicleRepository.findByVehicleTypeAndVehicleName(vehicleType, vehicleName);
		return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/type/{vehicleType}/name/{vehicleName}/color/{vehicleColor}")
	public ResponseEntity<Vehicle> seacrhVehicleV1(@PathVariable String vehicleType, @PathVariable String vehicleName,
			@PathVariable String vehicleColor) {
		Vehicle vehicle = vehicleRepository.findByVehicleTypeAndVehicleNameAndVehicleColor(vehicleType, vehicleName,
				vehicleColor);
		return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
	}

	@GetMapping("/search/all")
	public ResponseEntity<List<Vehicle>> searchAllVehicle() {
		List<Vehicle> vehicle = vehicleRepository.findAll();
		return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
	}

}
