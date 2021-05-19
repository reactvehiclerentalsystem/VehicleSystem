package com.capgemini.entities;

import javax.persistence.Column;
//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class VehicleBooking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	@Column(nullable=false)
	private String bookingStartDate;
	@Column(nullable = false)
	private String bookingEndDate;
	private boolean isCancelled; //specifies the status of booking , whether it is cancelled or not.

	@OneToOne 
	@JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId")
	private Vehicle vehicle;

	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private UserInfo userInfo;

}
