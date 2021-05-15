package com.capgemini.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int userId;
	
	private String userName;
	
	private String userNumber;
	
	private String userEmail;
	
	private String userPassword;
	
	private String userAdhaar;
	
	private String testimonial;
	
	private boolean isDeleted;
	
}
