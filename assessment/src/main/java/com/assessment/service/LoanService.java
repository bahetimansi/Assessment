package com.assessment.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.integration.ApiService;
import com.assessment.integration.LoanEmiResponse;
import com.assessment.model.EmiDetails;
import com.assessment.model.LoanDetails;
import com.assessment.model.LoanEmi;
import com.assessment.model.LoanResponse;
import com.assessment.repository.EmiDetailsRepository;
import com.assessment.repository.LoanDetailsRepository;
import com.assessment.repository.LoanEmiRepository;

@Service
public class LoanService {

	@Autowired
	private ApiService apiService;
	
	@Autowired
	private EmiDetailsRepository emiDetailsRepository;
	
	private LoanDetailsRepository loanDetailsRepository;
	
	private LoanEmiRepository loanEmiRepository;
	
	private final Integer DEFAULT_EMI_DAY = 15;
	
	public LoanResponse getLoanData(String loanAccountNumber) {
		LoanEmiResponse externalResponse = apiService.getData();
		return saveData(externalResponse);
	}
	
	private LoanResponse saveData(LoanEmiResponse externalResponse) {
		List<EmiDetails> emiDetailsList = new ArrayList<>();
		LoanDetails loanDetails = new LoanDetails();
		loanDetails.setLoanAccountNumber(externalResponse.getLoanAccountNumber());
		loanDetails.setEmiDay(DEFAULT_EMI_DAY);
		if(externalResponse.getEmiDetails() != null && !externalResponse.getEmiDetails().isEmpty()) {
			for(EmiDetails emiDetails : externalResponse.getEmiDetails()) {
				emiDetailsList.add(emiDetailsRepository.save(emiDetails));
			}
		}
		loanDetails = loanDetailsRepository.save(loanDetails);
		List<LoanEmi> loanEmiList = new ArrayList<>();
		EmiDetails dueEmi = new EmiDetails();
		for(EmiDetails emiDetails : emiDetailsList) {
			LoanEmi loanEmi = new LoanEmi();
			loanEmi.setLoanId(loanDetails.getId());
			loanEmi.setEmiId(emiDetails.getId());
			if(emiDetails.getDueStatus().equals(Boolean.TRUE)) {
				dueEmi = emiDetails;
			}
			loanEmiList.add(loanEmi);
		}
		loanEmiRepository.saveAll(loanEmiList);
		LoanResponse loanEmiResponse = new LoanResponse();
		loanEmiResponse.setEmiAmount(dueEmi.getEmiAmount());
		loanEmiResponse.setLoanAccountNumber(loanDetails.getLoanAccountNumber());
		String[] arr = dueEmi.getMonth().split(" ");
		int month = convertMonthNameToNumber(arr[0]);
		int year = Integer.parseInt(arr[1]);
		LocalDate date = LocalDate.of(year, month, loanDetails.getEmiDay());
		loanEmiResponse.setDueDate(date);
		return loanEmiResponse;
	}
	
	private int convertMonthNameToNumber(String monthName) {
        try {
            // Convert month name to uppercase and trim any extra spaces
            Month month = Month.valueOf(monthName.trim().toUpperCase(Locale.ENGLISH));
            return month.getValue(); // This will return the month number (1 = January, 2 = February, etc.)
        } catch (IllegalArgumentException e) {
            // Handle invalid month name
            System.err.println("Invalid month name: " + monthName);
            return -1; // Return -1 for invalid month
        }
    }
}
