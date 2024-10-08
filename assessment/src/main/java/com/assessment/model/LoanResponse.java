package com.assessment.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoanResponse {
	private String loanAccountNumber;
	
	private LocalDate dueDate;
	
	private Double emiAmount;
}
