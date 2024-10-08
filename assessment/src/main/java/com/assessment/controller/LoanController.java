package com.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.model.LoanResponse;
import com.assessment.service.LoanService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/loan")
@Slf4j
public class LoanController {
	
	@Autowired
	private LoanService loanService;
	
	@GetMapping("{loanAccountNumber}")
	public ResponseEntity<LoanResponse> getData(@PathVariable("loanAccountNumber") String loanAccountNumber ) {
		log.debug("Fetching loan data.....");
		LoanResponse loanResponse = loanService.getLoanData(loanAccountNumber);
		return new ResponseEntity<LoanResponse>(loanResponse, HttpStatus.OK);
	}
}
