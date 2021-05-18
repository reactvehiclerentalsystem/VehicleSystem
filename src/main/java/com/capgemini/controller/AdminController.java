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

import com.capgemini.entities.Admin;
import com.capgemini.entities.ContactUs;
import com.capgemini.entities.Queries;
import com.capgemini.entities.Testimonial;
import com.capgemini.entities.UserInfo;
import com.capgemini.entities.Vehicle;
import com.capgemini.entities.VehicleBooking;
import com.capgemini.entities.VehicleBrand;
import com.capgemini.exception.BrandNotFoundException;
import com.capgemini.exception.ContactIdNotFoundException;
import com.capgemini.exception.QueryIdMismatchException;
import com.capgemini.exception.TestimonialIdNotFoundException;
import com.capgemini.exception.UserIdNotFoundException;
import com.capgemini.exception.VehicleAlreadyBookedException;
import com.capgemini.exception.VehicleAvailableException;
import com.capgemini.exception.VehicleIdNotFoundException;
import com.capgemini.repository.AdminRepository;
import com.capgemini.repository.ContactUsRepository;
import com.capgemini.repository.QueriesRepository;
import com.capgemini.repository.TestimonialRepository;
import com.capgemini.repository.UserInfoRepository;
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

	@Autowired
	private QueriesRepository queriesRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private TestimonialRepository testimonialRepository;

	@PostMapping("/createadmin/")
	public String create(@RequestBody Admin admin) {
		adminRepository.save(admin);
		return "Record Added:";
	}

	@PostMapping("/postvehicle/{brand_id}")
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

	@PutMapping("/update/{vehicleId}/brand/{brand_id}")
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

	@PostMapping("/Createbooking/vehicle{vehicleId}/user/{userId}")
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

		vehicleBooking.setUserInfo(userInfo); // this will set userInfo in vehicleBooking.

		vehicleBooking.setCancelled(false);

		vehicleBooking.setVehicle(vehicle);// this will save the selected vehicle with booking user.

		vehicleBookingRepository.save(vehicleBooking);// it will get the vehicle booked for the user.
		return "Vehicle Booked!";
	}

	@PutMapping("/cancelbooking/booking{bookingId}")
	public String cancelVehicle(@PathVariable int bookingId) throws VehicleAvailableException {
		VehicleBooking vehicleBooking = vehicleBookingRepository.findById(bookingId).get();
		Vehicle vehicle = vehicleBooking.getVehicle();
		if (vehicle.isAvailable() == false && vehicleBooking.isCancelled() == false) {
			vehicle.setAvailable(true);
			vehicleBooking.setCancelled(true);
			vehicleBooking.setVehicle(vehicle);
			vehicleBookingRepository.save(vehicleBooking);
			return "Booking Cancelled";
		} else {
			throw new VehicleAvailableException("Vehicle is Available");
		}
	}

	@PostMapping("/create/VehicleBrand/")
	public String createVehicleBrand(@RequestBody VehicleBrand vehicleBrand) {
		vehicleBrandRepository.save(vehicleBrand);
		return "Vehicle Brand Created";

	}

	@PutMapping("/update/VehicleBrand/{brand_id}")
	public String updateVehicleBrand(@RequestBody VehicleBrand vb, @PathVariable int brand_id) {
		VehicleBrand vehicleBrand = vehicleBrandRepository.findById(brand_id).get();
		if (vehicleBrand != null) {
			vehicleBrand.setBrand_name(vb.getBrand_name());
			vehicleBrandRepository.save(vehicleBrand);
		}
		return "Vehicle Brand is Updated";

	}

	@PutMapping("/deletebrand/{brand_id}")
	public String deleteBrand(@PathVariable int brand_id) {

		VehicleBrand vehicle = vehicleBrandRepository.findById(brand_id).get();

		if (vehicle != null && vehicle.isDeleted() == false) // if vehicle id is present and vehicle status is not
																// deleted then the method will get accessed.
		{
			vehicle.setDeleted(true);
			vehicleBrandRepository.save(vehicle);// if vehicle is is present it will get deleted , hence
			// cancelled.
			return "Vehicle Brand Deleted By Admin!";
		} else {
			throw new BrandNotFoundException("Incorrect Brand Id! Enter correct Id!");
		}
	}

	@PutMapping("/updatecontact/{contactId}/email/{email}/mobile/{mobile}")
	public String updateContactUs(@PathVariable int contactId, @PathVariable String email, @PathVariable String mobile)
			throws ContactIdNotFoundException {
		ContactUs contactUs = contactUsRepo.findById(contactId).get();
		if (contactUs != null) {
			contactUs.setEmail(email);
			contactUs.setMobile(mobile);

			contactUsRepo.save(contactUs);
			return "Contact Us Updated by Admin!";
		} else {
			throw new ContactIdNotFoundException("ContactId Mismatch!");
		}

	}

	@PutMapping("/update/querysatus/{queryId}")
	public String updateQuery(@PathVariable int queryId) throws QueryIdMismatchException {
		Queries queries = queriesRepository.findById(queryId).get();
		if (queries != null) {
			queries.setQueryStatus("Query Checked");
			queriesRepository.save(queries);
			return "Query Status Updated by Admin!";
		} else {
			throw new QueryIdMismatchException("QueryId Mismatch , Query Not Found!");
		}
	}

	@GetMapping("/userInfo/{id}")
	public ResponseEntity<UserInfo> getUserInfoById(@PathVariable int id) throws UserIdNotFoundException {
		if (userInfoRepository.existsById(id)) {
			UserInfo userInfo = userInfoRepository.findById(id).get();
			return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
		} else {
			throw new UserIdNotFoundException("User Id not found!");
		}
	}

	@GetMapping("/search/allRegisteredUsers")
	public ResponseEntity<List<UserInfo>> getRegisteredUsers() throws UserIdNotFoundException {
		List<UserInfo> userInfo = userInfoRepository.findAll();
		if (userInfo != null) {
			return new ResponseEntity<List<UserInfo>>(userInfo, HttpStatus.OK);
		} else {
			throw new UserIdNotFoundException("Registered User not present of given Id.");
		}
	}

	@PutMapping("/manage/testimonial/{id}")
	public String manageTestimonial(@PathVariable int id) throws TestimonialIdNotFoundException {
		Testimonial testimonial = testimonialRepository.findById(id).get();
		if (testimonial != null) {
			testimonial.setTestimonialStatus(true);
			testimonialRepository.save(testimonial);
			return "Testimonial status updated by Admin";
		} else {
			throw new TestimonialIdNotFoundException("Testimonial Id not found!");
		}
	}

	@GetMapping("/get/alltestimonial/")
	public ResponseEntity<List<Testimonial>> getAllTestimonial() throws TestimonialIdNotFoundException {
		List<Testimonial> list = testimonialRepository.findAll();
		if (list != null) {
			return new ResponseEntity<List<Testimonial>>(list, HttpStatus.OK);
		}else {
			throw new TestimonialIdNotFoundException("No testimonial is yet updated.");
		}
	}

	//ADMIN DASHBOARD
	
	@GetMapping("/get/totalregistereduser/")
	public String countTotalRegUser() {
		long count = userInfoRepository.count();
		return "total registered user: " + count;
	}

	@GetMapping("/get/totalvehiclebooking/")
	public String countTotalBooking() {
		long count = vehicleBookingRepository.count();
		return "total vehicle booking: " + count;
	}

	@GetMapping("/get/totalqueries/")
	public String countTotalQueries() {
		long count = queriesRepository.count();
		return "total queries: " + count;
	}

	@GetMapping("/get/totaltestimonial/")
	public String countTotalTestimonial() {
		long count = testimonialRepository.count();
		return "total testimonial: " + count;
	}
}
