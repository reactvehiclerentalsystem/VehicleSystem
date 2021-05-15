package com.capgemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.capgemini.entities.UserInfo;
import com.capgemini.exception.UserIdNotFoundException;
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
	public ResponseEntity<UserInfo> findById(@PathVariable int id) throws UserIdNotFoundException {
		if(userInfoRepository.existsById(id)) {
			UserInfo userInfo=userInfoRepository.findById(id).get();
			return new ResponseEntity<UserInfo>(userInfo,HttpStatus.OK);
		}else {
			throw new UserIdNotFoundException("User Id not found!");
		}
	}
	
	@PutMapping("/update/{id}")
	public String update(@PathVariable int id, @RequestBody UserInfo ui) throws UserIdNotFoundException {
		
		UserInfo dbUser =  userInfoRepository.findById(id).get();
		if(dbUser != null && dbUser.isDeleted()==false) {
			userInfoRepository.save(dbUser);
			return "User Profile Updated!!";
		}
		else {
			throw new UserIdNotFoundException("User Id not found!");
		}
	}
	
	@DeleteMapping("/{id}")
	public String deleteRecord(@PathVariable int id) throws UserIdNotFoundException {
		UserInfo userInfo=userInfoRepository.findById(id).get();
		if(userInfo!=null && userInfo.isDeleted()==false) {
			userInfo.setDeleted(true);
			userInfoRepository.save(userInfo);
			return "Record Deleted";
		}else {
			throw new UserIdNotFoundException("User Id not found!");
		}
	}
}
