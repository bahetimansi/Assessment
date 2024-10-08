package com.assessment.integration;

import java.util.List;

import com.assessment.model.EmiDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoanEmiResponse {
	private String loanAccountNumber;
    private List<EmiDetails> emiDetails;
}
