package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Vehicle;
import com.capgemini.entities.VehicleBrand;
import com.capgemini.exception.BrandNotFoundException;
import com.capgemini.exception.ListIsEmptyException;
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

	@PostMapping("/{brand_id}")
	public String addVehicle(@RequestBody Vehicle vehicle, @PathVariable int brand_id) {

		VehicleBrand vehicleBrand = vehicleBrandRepository.findById(brand_id).get();
		if (vehicleBrand.isDeleted() == true) {
			throw new BrandNotFoundException("Brand Not Found!!");
		} else {
			vehicle.setVehicleBrand(vehicleBrand);
			vehicle.setDeleted(false);

			vehicleRepository.save(vehicle);
			return "Vehicle Added";
		}
	}

	@PutMapping("/{vehicleId}/brand/{brand_id}")
	public String updateVehicle(@RequestBody Vehicle v, @PathVariable int vehicleId, @PathVariable int brand_id)
			throws VehicleIdNotFoundException {
		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
		VehicleBrand vehicleBrand = vehicleBrandRepository.findById(brand_id).get();
		if (vehicle != null && vehicle.isDeleted() == false) {
			vehicle.setVehiclePlateNumber(v.getVehiclePlateNumber());
			vehicle.setVehicleName(v.getVehicleName());
			vehicle.setVehicleType(v.getVehicleType());
			vehicle.setVehicleColor(v.getVehicleColor());
			vehicle.setVehicleLocation(v.getVehicleLocation());
			vehicle.setNumberOfSeats(v.getNumberOfSeats());
			vehicle.setDailyPrice(v.getDailyPrice());
			vehicle.setAvailable(true);
			vehicle.setVehicleBrand(vehicleBrand);
			vehicle.setDeleted(false);
			vehicleRepository.save(vehicle);
			return "Vehicle Updated!";
		} else {

			throw new VehicleIdNotFoundException("Incorrect Id! Enter correct Id!");
		}

	}

	@DeleteMapping("/{vehicleId}")
	public String removeVehicle(@PathVariable int vehicleId) throws VehicleIdNotFoundException {

		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();

		if (vehicle != null && vehicle.isDeleted() == false) // if vehicle id is present and vehicle status is not
																// deleted then the method will get accessed.
		{

			vehicle.setAvailable(false);
			vehicle.setDeleted(true);
			vehicleRepository.save(vehicle);// if vehicle is is present it will get deleted , hence
											// cancelled.
			return "Vehicle Deleted!";
		} else {
			throw new VehicleIdNotFoundException("Incorrect Id! Enter correct Id!");
		}

	}

	@GetMapping("/name/{vehicleName}/type/{vehicleType}/color/{vehicleColor}/seats/{numberOfSeats}/location/{vehiclelocation}")
	public ResponseEntity<List<Vehicle>> seacrhVehicleV1(@PathVariable String vehicleName,@PathVariable String vehicleType,
			 @PathVariable String vehicleColor,@PathVariable int numberOfSeats,@PathVariable String vehiclelocation) throws ListIsEmptyException {
		List<Vehicle> vehicle = vehicleRepository.findByVehicleNameAndVehicleTypeAndVehicleColorAndNumberOfSeatsAndVehicleLocation(vehicleName,vehicleType, vehicleColor, numberOfSeats,vehiclelocation);
		if (vehicle.size() != 0) {
			return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("Vehicle is not present.");
		}
	}

	@GetMapping("/allVehicles")
	public ResponseEntity<List<Vehicle>> searchAllVehicle() throws ListIsEmptyException {
		List<Vehicle> vehicle = vehicleRepository.findAll();
		if (vehicle.size() != 0) {
			return new ResponseEntity<List<Vehicle>>(vehicle, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("No vehicle is registered.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Vehicle> searchVehicleById(@PathVariable int id) throws ListIsEmptyException {
		Vehicle vehicle = vehicleRepository.findById(id).get();
		if (vehicle!= null) {
			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("No vehicle is registered.");
		}
	}
}
