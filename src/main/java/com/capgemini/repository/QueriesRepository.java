package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.Queries;

public interface QueriesRepository extends JpaRepository<Queries, Integer> {

}