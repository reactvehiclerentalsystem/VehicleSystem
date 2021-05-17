package com.capgemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.entities.VehicleBrand;
import com.capgemini.exception.BrandNotFoundException;
import com.capgemini.repository.VehicleBrandRepository;

@Service
public class VehicleBrandImpl implements IVehicleBrandService {

	@Autowired
	VehicleBrandRepository vehicleBrandRepository;

	@Override
	public String createBrand(VehicleBrand vehicleBrand) {
		vehicleBrandRepository.save(vehicleBrand);

		return "Brand Added";
	}

	@Override
	public String updateBrand(int brand_id, VehicleBrand vehicleBrand) {
		
		if(!vehicleBrandRepository.existsById(brand_id)) {
			throw new BrandNotFoundException("Check the id and Try again");
		}
		VehicleBrand v = vehicleBrandRepository.findById(brand_id).get();
			v.setBrand_name(vehicleBrand.getBrand_name());
			vehicleBrandRepository.save(v);
		
		return "Brand Updated";	
	}
	

	@Override
	public String deleteBrand(int brand_id) {
		vehicleBrandRepository.deleteById(brand_id);
		return "Brand Deleted";
	}

}
