package com.capgemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Queries;
import com.capgemini.repository.QueriesRepository;

@RestController
@RequestMapping("/api/queries/")
public class QueriesController {
	@Autowired
	private QueriesRepository queriesRepository;

	@PostMapping("/post/{query}")
	public String postQuery(@PathVariable String query) {
		Queries queries = new Queries();
		queries.setQuery(query);
		queriesRepository.save(queries);
		return "Query Posted";
	}
}