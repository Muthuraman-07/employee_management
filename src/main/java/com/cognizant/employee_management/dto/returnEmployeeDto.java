package com.cognizant.employee_management.dto;

import java.time.LocalDate;

import com.cognizant.employee_management.model.Shift;

import lombok.Data;

@Data
public class returnEmployeeDto {
	    private int employeeId;
	    private int managerId;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String phoneNumber;
	    private String department;
	    private String role;
	    private Shift shift;
	    private LocalDate joinedDate;
}
