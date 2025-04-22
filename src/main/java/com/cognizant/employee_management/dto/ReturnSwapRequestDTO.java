package com.cognizant.employee_management.dto;

import lombok.Data;

@Data
public class ReturnSwapRequestDTO {
	private int id;
	private int requestedShiftId;
	private String status;
	private boolean approvedByManager;
}
