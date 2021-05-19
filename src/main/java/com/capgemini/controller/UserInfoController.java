package com.capgemini.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Testimonial;
import com.capgemini.entities.UserInfo;
import com.capgemini.exception.UserIdNotFoundException;
import com.capgemini.repository.TestimonialRepository;
import com.capgemini.repository.UserInfoRepository;

@RestController
@RequestMapping("/api/info/")
public class UserInfoController {
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private TestimonialRepository testimonialRepository;

	@PostMapping("/")
	public String create(@RequestBody UserInfo user) {

		user.setDeleted(false);
		userInfoRepository.save(user);
		return "added";
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Optional<UserInfo>> findById(@PathVariable int id) throws UserIdNotFoundException {
		if (userInfoRepository.existsById(id)) {
			Optional<UserInfo> userInfo = userInfoRepository.findById(id);
			return new ResponseEntity<Optional<UserInfo>>(userInfo, HttpStatus.OK);
		} else {
			throw new UserIdNotFoundException("User Id not found!");
		}

	}

	@PutMapping("/user/{id}")
	public String update(@PathVariable int id, @RequestBody UserInfo ui) throws UserIdNotFoundException {

		UserInfo dbUser = userInfoRepository.findById(id).get();
		if (dbUser != null && dbUser.isDeleted() == false) {
			dbUser.setUserName(ui.getUserName());
			dbUser.setUserNumber(ui.getUserNumber());
			dbUser.setUserEmail(ui.getUserEmail());
			dbUser.setUserPassword(ui.getUserPassword());
			dbUser.setUserAdhaar(ui.getUserAdhaar());
			dbUser.setDeleted(false);
			userInfoRepository.save(dbUser);
			return "User Profile Updated!!";
		} else {
			throw new UserIdNotFoundException("User Id not found!");
		}
	}

	@PutMapping("/delete/{id}")
	public String deleteRecord(@PathVariable int id) throws UserIdNotFoundException {
		UserInfo userInfo = userInfoRepository.findById(id).get();
		if (userInfo != null && userInfo.isDeleted() == false) {
			userInfo.setDeleted(true);
			userInfoRepository.save(userInfo);
			return "Record Deleted";
		} else {
			throw new UserIdNotFoundException("User Id not found!");
		}
	}

	@PostMapping("/post/testimonial/{testimonials}")
	public String postTestimonial(@PathVariable String testimonials) {
		Testimonial testimonial = new Testimonial();
		testimonial.setTestimonials(testimonials);
		testimonialRepository.save(testimonial);
		return "Testimonial posted by Registered user";
	}
}
