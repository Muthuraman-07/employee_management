package com.cognizant.employee_management.dto;

import java.time.LocalDateTime;

import com.cognizant.employee_management.model.Employee;

import lombok.Data;
@Data
public class LeaveDto {
	private int leaveId;
    private Employee employee;
	private LocalDateTime appliedDate;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String status;
	private String leaveType;
	private LocalDateTime approvedDate;
}
