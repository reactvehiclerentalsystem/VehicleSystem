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

import com.capgemini.entities.UserInfo;
import com.capgemini.entities.Vehicle;
import com.capgemini.entities.VehicleBooking;
import com.capgemini.exception.VehicleIdNotFoundException;
import com.capgemini.repository.UserInfoRepository;
import com.capgemini.repository.VehicleBookingRepository;
import com.capgemini.repository.VehicleRepository;

@RestController
@RequestMapping("/api/booking")
public class VehicleBookingController {

	@Autowired
	private VehicleBookingRepository vehicleBookingRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;

	@PostMapping("/book/vehicle{vehicleId}/user/{userId}")
	public String bookVehicle(@RequestBody VehicleBooking vehicleBooking, @PathVariable int vehicleId,@PathVariable int userId)
			throws VehicleIdNotFoundException {
		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
		UserInfo userInfo=userInfoRepository.findById(userId).get();
		if (vehicle != null && userInfo.isDeleted()==false) {
			
			//vehicleBooking.setCancelled(false);

			vehicle.setAvailable(false); // this will set the availability of vehicle to other users as booked.
			
			vehicleBooking.setUserInfo(userInfo); //this will set userInfo in vehicleBooking.
			
			vehicleBooking.setCancelled(false);
			
			vehicleBooking.setVehicle(vehicle);// this will save the selected vehicle with booking user.

			vehicleBookingRepository.save(vehicleBooking);// it will get the vehicle booked for the user.
			return "Vehicle Booked!";
		} else {
			throw new VehicleIdNotFoundException("Incorrect Id! Enter correct Id!");
		}
	}

	@PutMapping("/delete/{bookingId}")
	public String cancelBooking(@PathVariable int bookingId) throws VehicleIdNotFoundException {

		VehicleBooking vehicleBooking = vehicleBookingRepository.findById(bookingId).get();

		if (vehicleBooking != null && vehicleBooking.isCancelled() == false) {
			vehicleBooking.setCancelled(true);
			vehicleBookingRepository.save(vehicleBooking);// if vehicle is is present it will get deleted , hence
															// cancelled.
			return "Booking Cancelled!";
		} else {
			throw new VehicleIdNotFoundException("Incorrect Id! Enter correct Id!");
		}

	}

	@GetMapping("/details/all")
	public ResponseEntity<List<VehicleBooking>> bookingDetails() {
		List<VehicleBooking> vehicleBooking = vehicleBookingRepository.findAll();
		return new ResponseEntity<List<VehicleBooking>>(vehicleBooking, HttpStatus.OK);
	}

	@GetMapping("/details/{bookingId}")
	public ResponseEntity<VehicleBooking> bookingDetails(@PathVariable int bookingId) {
		VehicleBooking vehicleBooking = vehicleBookingRepository.findById(bookingId).get();
		return new ResponseEntity<VehicleBooking>(vehicleBooking, HttpStatus.OK);
	}
}
