package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.ContactUs;

public interface ContactUsRepository extends JpaRepository<ContactUs, Integer> {
}
