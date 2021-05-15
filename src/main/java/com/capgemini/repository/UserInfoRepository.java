package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

}
