package com.capgemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Admin;
import com.capgemini.entities.ContactUs;
import com.capgemini.entities.Vehicle;
import com.capgemini.entities.VehicleBooking;
import com.capgemini.entities.VehicleBrand;
import com.capgemini.exception.VehicleIdNotFoundException;
import com.capgemini.repository.AdminRepository;
import com.capgemini.repository.ContactUsRepository;
import com.capgemini.repository.VehicleBookingRepository;
import com.capgemini.repository.VehicleBrandRepository;
import com.capgemini.repository.VehicleRepository;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private VehicleBookingRepository vehicleBookingRepository;

	@Autowired
	private VehicleBrandRepository vehicleBrandRepository;
	
	@Autowired
	private ContactUsRepository contactUsRepo;

	@PostMapping("/createadmin/")
	public String create(@RequestBody Admin admin) {
		adminRepository.save(admin);
		return "Record Added:";
	}

	@PostMapping("/postvehicle/")
	public String postVehicle(@RequestBody Vehicle vehicle) {
		vehicleRepository.save(vehicle);
		return "Vehicle Posted";
	}

	@PutMapping("/updatevehicle/{vehicleId}")
	public String updateVehicle(@PathVariable int vehicleId, @RequestBody Vehicle v) {
		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
		if (vehicle != null) {
			vehicle.setVehiclePlateNumber(v.getVehiclePlateNumber());
			vehicle.setVehicleName(v.getVehicleName());
			vehicle.setVehicleType(v.getVehicleType());
			vehicle.setVehicleColor(v.getVehicleColor());
			vehicle.setVehicleLocation(v.getVehicleLocation());
			vehicle.setNumberOfSeats(v.getNumberOfSeats());
			vehicle.setDailyPrice(v.getDailyPrice());

			vehicleRepository.save(vehicle);
		}
		return "Vehicle Updated";

	}

	@PutMapping("/deletevehicle/{vehicleId}")
	public String removeVehicle(@PathVariable int vehicleId) throws VehicleIdNotFoundException {

		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();

		if (vehicle != null && vehicle.isDeleted() == false) // if vehicle id is present and vehicle status is not
																// deleted then the method will get accessed.
		{

			vehicle.setDeleted(true);
			vehicleRepository.save(vehicle);// if vehicle is is present it will get deleted , hence
											// cancelled.
			return "Vehicle Deleted!";
		} else {
			throw new VehicleIdNotFoundException("Incorrect Id! Enter correct Id!");
		}

	}

	@PostMapping("/Admin/Managebooking/vehicle{vehicleId}")
	public String bookVehicle(@RequestBody VehicleBooking vehicleBooking, @PathVariable int vehicleId) {
		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
		if (vehicle.isAvailable() == true) {
			vehicle.setAvailable(false);
			vehicleBooking.setVehicle(vehicle);
			vehicleBookingRepository.save(vehicleBooking);
			return "Vehicle Booked";
		} else {
			return "Vehicle is already Booked";
		}

	}

	@PutMapping("/Admin/cancelbooking/booking{bookingId}")
	public String cancelVehicle(@PathVariable int bookingId) {
		VehicleBooking vehicleBooking = vehicleBookingRepository.findById(bookingId).get();
		Vehicle vehicle = vehicleBooking.getVehicle();
		if (vehicle.isAvailable() == false && vehicleBooking.isCancelled() == false) {
			vehicle.setAvailable(true);
			vehicleBooking.setCancelled(true);
			vehicleBooking.setVehicle(vehicle);
			vehicleBookingRepository.save(vehicleBooking);
			return "Booking Cancelled";
		} else {
			return "Vehicle is Available";
		}
	}

	@PostMapping("/Admin/create/VehicleBrand/")
	public String createVehicleBrand(@RequestBody VehicleBrand vehicleBrand) {
		vehicleBrandRepository.save(vehicleBrand);
		return "Vehicle Brand Created";

	}

	@PutMapping("/Admin/update/VehicleBrand/{brand_id}")
	public String updateVehicleBrand(@RequestBody VehicleBrand vb, @PathVariable int brand_id) {
		VehicleBrand vehicleBrand = vehicleBrandRepository.findById(brand_id).get();
		if (vehicleBrand != null) {
			vehicleBrand.setBrand_name(vb.getBrand_name());
			vehicleBrandRepository.save(vehicleBrand);
		}
		return "Vehicle Brand is Updated";

	}

	@DeleteMapping("/delete/VelhicleBrand/{brand_id}/")
	public String deleteVehicleBrand(@PathVariable int brand_id) {
		vehicleBrandRepository.deleteById(brand_id);
		return "Vehicle Brand is Deleted";
	}
	
	@PutMapping("/updatecontact/{contactId}/email/{email}/mobile/{mobile}")
	public String updateContactUs(@PathVariable int contactId,@PathVariable String email,@PathVariable String mobile) {
		ContactUs contactUs=contactUsRepo.findById(contactId).get();
		contactUs.setEmail(email);
		contactUs.setMobile(mobile);
		
		contactUsRepo.save(contactUs);
		return "Contact Us Updated";
	}

}
