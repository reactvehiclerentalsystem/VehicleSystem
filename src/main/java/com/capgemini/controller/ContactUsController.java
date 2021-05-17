package com.capgemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.ContactUs;
import com.capgemini.repository.ContactUsRepository;

@RestController
@RequestMapping("/api/contactus/")
public class ContactUsController {
	@Autowired
	private ContactUsRepository contactUsRepo;

	@PostMapping("/addcontact/")
	public String addContactUs(@RequestBody ContactUs contactUs) {
		contactUsRepo.save(contactUs);
		return "Contact Us added";
	}
}
