package com.capgemini.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.UserInfo;
import com.capgemini.entities.Vehicle;
import com.capgemini.entities.VehicleBooking;
import com.capgemini.exception.ListIsEmptyException;
import com.capgemini.exception.UserIdNotFoundException;
import com.capgemini.exception.VehicleIdNotFoundException;
import com.capgemini.repository.UserInfoRepository;
import com.capgemini.repository.VehicleBookingRepository;
import com.capgemini.repository.VehicleRepository;

@RestController
@RequestMapping("/bookings")
public class VehicleBookingController {

	@Autowired
	private VehicleBookingRepository vehicleBookingRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;

	@PostMapping("/vehicle/{vehicleId}/user/{userId}")
	public String bookVehicle(@RequestBody VehicleBooking vehicleBooking, @PathVariable int vehicleId,
			@PathVariable int userId) throws VehicleIdNotFoundException, UserIdNotFoundException {
		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
		UserInfo userInfo = userInfoRepository.findById(userId).get();

		if (vehicle == null) {
			throw new VehicleIdNotFoundException("Vehicle Not Found!!");
		} else if (vehicle.isDeleted() == true && vehicle.isAvailable() == false) {
			throw new VehicleIdNotFoundException("Vehicle is currently unavailable!!");
		} else if (userInfo.isDeleted() == true) {
			throw new UserIdNotFoundException("Invalid User Id!!");
		}

		vehicle.setAvailable(false); // this will set the availability of vehicle to other users as booked.

		vehicleBooking.setUserInfo(userInfo); // this will assign the person who is booking in vehicleBooking.

		vehicleBooking.setCancelled(false); // this will save the booking status as active for the specified booking id.

		vehicleBooking.setVehicle(vehicle);// this will save the selected vehicle with booking user.

		vehicleBookingRepository.save(vehicleBooking);// it will get the vehicle booked for the user.
		return "Vehicle Booked!";
	}

	@DeleteMapping("/{bookingId}")
	public String cancelBooking(@PathVariable int bookingId) throws VehicleIdNotFoundException {

		VehicleBooking vehicleBooking = vehicleBookingRepository.findById(bookingId).get();
		Vehicle vehicle = vehicleBooking.getVehicle();

		if (vehicleBooking != null && vehicleBooking.isCancelled() == false) {
			vehicleBooking.setCancelled(true);
			vehicle.setAvailable(true);
			/*
			 * if vehicle is present, it will be made available to other users by making
			 * setAvailable true, and the bookingCancelled status will be made true.
			 */
			vehicleBookingRepository.save(vehicleBooking);
			return "Booking Cancelled!";
		} else {
			throw new VehicleIdNotFoundException("Incorrect Id! Enter correct Id!");
		}

	}

	@GetMapping("/")
	public ResponseEntity<List<VehicleBooking>> bookingDetails() throws ListIsEmptyException {
		List<VehicleBooking> vehicleBooking = vehicleBookingRepository.findAll();
		if (vehicleBooking != null) {
			return new ResponseEntity<List<VehicleBooking>>(vehicleBooking, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("No Booking is done yet.");
		}
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Optional<VehicleBooking>> bookingDetails(@PathVariable int bookingId)
			throws ListIsEmptyException {
		Optional<VehicleBooking> vehicleBooking = vehicleBookingRepository.findById(bookingId);
		if (vehicleBooking != null) {
			return new ResponseEntity<Optional<VehicleBooking>>(vehicleBooking, HttpStatus.OK);
		} else {
			throw new ListIsEmptyException("No Booking is done with the specified ID.");
		}
	}
}
