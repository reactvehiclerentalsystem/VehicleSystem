
package com.capgemini.controller;

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
//import com.capgemini.entities.Vehicle;
import com.capgemini.entities.VehicleBrand;
import com.capgemini.exception.BrandNotFoundException;
import com.capgemini.exception.VehicleIdNotFoundException;
import com.capgemini.repository.VehicleBrandRepository;
import com.capgemini.repository.VehicleRepository;


@RestController
@RequestMapping("/api/brand/")
public class VehicleBrandController {

	@Autowired
	VehicleBrandRepository vehicleBrandRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	@PostMapping("/create/")
	public ResponseEntity<String> createBrand(@RequestBody VehicleBrand vehicleBrand) {

		vehicleBrandRepository.save(vehicleBrand);

		return new ResponseEntity<>("Brand Added", HttpStatus.OK);
	}

	@PutMapping("/update/{brand_id}")
	public ResponseEntity<String> updateBrand(@PathVariable int brand_id, @RequestBody VehicleBrand vehicleBrand)
			throws BrandNotFoundException {

		if (vehicleBrand.isDeleted()==true) {
			throw new BrandNotFoundException("Brand Not Found!!");
		}
		VehicleBrand v = vehicleBrandRepository.findById(brand_id).get();
		v.setBrand_name(vehicleBrand.getBrand_name());
		vehicleBrandRepository.save(v);

		return new ResponseEntity<>("Brand Updated", HttpStatus.OK);
	}


	@PutMapping("/delete/{brand_id}")
	public String deleteBrand(@PathVariable int brand_id) {

		VehicleBrand vehicle = vehicleBrandRepository.findById(brand_id).get();

		if (vehicle != null && vehicle.isDeleted() == false) // if vehicle id is present and vehicle status is not
																// deleted then the method will get accessed.
		{
			vehicle.setDeleted(true);
			vehicleBrandRepository.save(vehicle);// if vehicle is is present it will get deleted , hence
											// cancelled.
			return "Vehicle Brand Deleted!";
		} else {
			throw new BrandNotFoundException("Incorrect Brand Id! Enter correct Id!");
		}
	}
}
