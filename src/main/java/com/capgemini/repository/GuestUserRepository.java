package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.GuestUser;
import com.capgemini.entities.Vehicle;

public interface GuestUserRepository extends JpaRepository<GuestUser, Integer> {

	Vehicle findByVehicleName(String vehicleName);

	Vehicle findByVehicleType(String vehicleType);

	Vehicle findByVehicleLocation(String vehiclelocation);

	Vehicle findByNumberOfSeats(int numberOfSeats);

	Vehicle findByVehicleTypeAndVehicleName(String vehicleType, String vehicleName);

	Vehicle findByVehicleTypeAndVehicleNameAndVehicleColor(String vehicleType, String VehicleName, String vehicleColor);

}
