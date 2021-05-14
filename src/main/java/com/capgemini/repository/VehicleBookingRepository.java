package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.VehicleBooking;

public interface VehicleBookingRepository extends JpaRepository<VehicleBooking, Integer> {

}
