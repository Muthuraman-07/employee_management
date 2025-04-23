package com.cognizant.employeemanagement.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReturnEmployeeDto {
	    private int employeeId;
	    private int managerId;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String phoneNumber;
	    private String department;
	    private String role;
	    private int shiftId;
	    private LocalDate joinedDate;
}
