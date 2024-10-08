package com.assessment.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApiService {

	@Value("${external.api.url}")
	private String externalApiUrl;

	@Autowired
	private RestTemplate restTemplate;

	public LoanEmiResponse getData() {

		ResponseEntity<String> response = restTemplate.getForEntity(externalApiUrl, String.class);
		return parseLoanAccountResponse(response.getBody());

	}

	private LoanEmiResponse parseLoanAccountResponse(String responseBody) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(responseBody, LoanEmiResponse.class);
		} catch (Exception e) {
			System.err.println("Failed to parse response: " + e.getMessage());
			return null; 
		}
	}
}
