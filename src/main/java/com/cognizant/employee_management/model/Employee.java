package com.cognizant.employee_management.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Employee {
	private int employeeId;
	private int managerId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String department;
	private String role;
	private LocalDate joinedDate;
}
