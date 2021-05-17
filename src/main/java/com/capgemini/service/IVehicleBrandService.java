package com.capgemini.service;

import com.capgemini.entities.VehicleBrand;

public interface IVehicleBrandService {
	
	public String createBrand(VehicleBrand vehicleBrand);
	
	public String updateBrand(int brand_id, VehicleBrand vehicleBrand);
	
	public String deleteBrand(int brand_id);


}
