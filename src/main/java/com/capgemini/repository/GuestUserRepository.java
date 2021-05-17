package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.GuestUser;

public interface GuestUserRepository extends JpaRepository<GuestUser, Integer> {

}
