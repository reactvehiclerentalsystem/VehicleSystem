package com.capgemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.capgemini.entities.UserInfo;
import com.capgemini.repository.UserInfoRepository;

@RestController
@RequestMapping("/api/info/")
public class UserInfoController {
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@PostMapping("/")
	public String create(@RequestBody UserInfo user) {
		
		userInfoRepository.save(user);
		return "added";
	}
	@GetMapping("/{id}")
	public UserInfo findById(@PathVariable int id) {
		return userInfoRepository.findById(id).get();
	}
	
	@GetMapping("/update/{id}")
	public String update(@PathVariable int id, @RequestBody UserInfo ui) {
		
		UserInfo dbUser =  userInfoRepository.findById(id).get();
		if(dbUser != null) {
			dbUser.setEmail(ui.getEmail());
			dbUser.setNumber(ui.getNumber());
			dbUser.setPassword(ui.getPassword());
			
			userInfoRepository.save(dbUser);
		}
		
		return "User Profile Updated!!";
	}
	
	@DeleteMapping("/{id}")
	public String deleteRecord(@PathVariable int id) {
		userInfoRepository.deleteById(id);
		return "Record Deleted";
		
		
	}

}
