package com.assessment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="emi_details")
@Getter
@Setter
@NoArgsConstructor
public class EmiDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "month")
	private String month;
	
	@Column(name = "emi_amount")
	private Double emiAmount;
	
	@Column(name = "paid_status")
	private Boolean paidStatus;
	
	@Column(name = "due_status")
	private Boolean dueStatus;
}
