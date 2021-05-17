package com.capgemini.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Queries {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int queryId;
	private String query;
	private String queryStatus = "Query Unchecked";
}