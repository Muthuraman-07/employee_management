package com.cognizant.employeemanagement.dto;

import lombok.Data;

@Data
public class ReturnSwapRequestDTO {
	private int id;
	private int requestedShiftId;
	private String status;
	private boolean approvedByManager;
}
