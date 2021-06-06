package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.GuestUser;
import com.capgemini.entities.Queries;
import com.capgemini.entities.UserInfo;
import com.capgemini.entities.Vehicle;
import com.capgemini.exception.ListIsEmptyException;
import com.capgemini.repository.GuestUserRepository;
import com.capgemini.repository.QueriesRepository;
import com.capgemini.repository.UserInfoRepository;
import com.capgemini.repository.VehicleRepository;

@RestController
@RequestMapping("/api/guest")
public class GuestUserController {
	
	@Autowired
	private GuestUserRepository guestUserRepository;

	@Autowired
	private QueriesRepository queriesRepository;

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@PostMapping("/create")
	public String createGuestUser(@RequestBody GuestUser user) {
		guestUserRepository.save(user);
		
		return "Guset User Created";
		
	}
	
	@PostMapping("/register")
	public String registerUser(@RequestBody UserInfo user) {

		user.setDeleted(false);
		userInfoRepository.save(user);
		return "added";
	}


	@PostMapping("/post/{query}")
	public String postQuery(@PathVariable String query) {
		Queries queries = new Queries();
		queries.setQuery(query);
		queriesRepository.save(queries);
		return "Query Posted";
	}
}
