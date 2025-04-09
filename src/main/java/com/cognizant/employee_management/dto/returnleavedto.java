package com.cognizant.employee_management.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class returnleavedto {
	private int leaveId;
	private LocalDateTime appliedDate;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String leaveType;
	private String status;
	private LocalDateTime approvedDate;
}
