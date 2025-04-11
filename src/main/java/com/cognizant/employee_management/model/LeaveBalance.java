package com.cognizant.employee_management.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class LeaveBalance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int leaveBalanceID;
	
	@ManyToOne
	@JoinColumn(name="employeeId")
	@NotNull(message = "Employee should not be null")
    private Employee employee;
	
	@Column(length = 20)
	private String leaveType;
	private int balance;

	@NotNull(message = "Leave type should not be null")
	@Size(min = 2, max = 20, message = "Leave type must be between 2 and 20 characters")
	private String LeaveType;
	
	@NotNull(message = "Balance should not be null")
	@PositiveOrZero(message = "Balance must be zero or positive")
	private int Balance;

}