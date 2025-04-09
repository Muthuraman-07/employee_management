package com.cognizant.employee_management.dto;

import com.cognizant.employee_management.model.Shift;

import lombok.Data;
@Data
public class EmployeeDto {
	private int employeeId;
	private int managerId;
	private String firstName;
	private String lastName;
	private String department;
	private String role;
    private Shift shift;
}
