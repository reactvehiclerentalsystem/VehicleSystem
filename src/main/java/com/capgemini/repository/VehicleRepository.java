package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

//	List<Vehicle> findByVehicleName(String vehicleName);

//	List<Vehicle> findByVehicleType(String vehicleType);

//	List<Vehicle> findByVehicleLocation(String vehiclelocation);

//	List<Vehicle> findByNumberOfSeats(int numberOfSeats);

	List<Vehicle> findByVehicleNameAndVehicleTypeAndVehicleColorAndNumberOfSeatsAndVehicleLocation(String vehicleName,String vehicleType, String vehicleColor,int numberOfSeats,String vehiclelocation);

}
