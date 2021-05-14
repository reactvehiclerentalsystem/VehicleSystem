package com.capgemini.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "vehicleId", scope = Integer.class)
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vehicleId;
	private String vehiclePlateNumber;
	private String vehicleName;
	private String vehicleType;
	private String vehicleColor;
	private String vehicleLocation;
	private int numberOfSeats;
	private double dailyPrice;
	private boolean isAvailable; //specifies availability of vehicle!

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
	private VehicleBrand vehicleBrand; 
}
