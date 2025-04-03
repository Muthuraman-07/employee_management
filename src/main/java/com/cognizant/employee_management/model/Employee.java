package com.cognizant.employee_management.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Employee {
	@Id
	private int employeeId;
	private int managerId;
	@Column(length = 50)
	private String username;
	@Column(length = 50)
	private String password;
	@Column(length = 50)
	private String firstName;
	@Column(length = 50)
	private String lastName;
	@Column(length = 50)
	private String email;
	@Column(length = 12)
	private String phoneNumber;
	@Column(length = 50)
	private String department;
	@Column(length = 50)
	private String role;
	@ManyToOne
	@JoinColumn(name="shiftId")
    private Shift shift;
	private LocalDate joinedDate;
}