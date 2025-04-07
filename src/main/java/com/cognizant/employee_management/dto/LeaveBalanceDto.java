package com.cognizant.employee_management.dto;

import com.cognizant.employee_management.model.Employee;

import lombok.Data;
@Data
public class LeaveBalanceDto {
	private int LeaveBalanceID;
    private Employee employee;
	private String LeaveType;
	private int Balance;
}
