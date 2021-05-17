package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.Testimonial;

public interface TestimonialRepository extends JpaRepository<Testimonial, Integer> {

}
