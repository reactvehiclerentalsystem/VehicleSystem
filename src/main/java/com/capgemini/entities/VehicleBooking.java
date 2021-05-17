package com.capgemini.entities;

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
	private String bookingStartDate;
	private String bookingEndDate;
	private boolean isCancelled;

	@OneToOne // (cascade=CascadeType.ALL)
	@JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId")
	private Vehicle vehicle;

	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private UserInfo userInfo;

}
