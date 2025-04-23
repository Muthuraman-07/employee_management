package com.cognizant.employeemanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class LeaveBalance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int leaveBalanceID;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "employeeId")
	@JsonIgnore
	private Employee employee;

	@Column(length = 20)
	private String leaveType;

	private int balance;

	public LeaveBalance(Employee employee, String leaveType, int balance) {
		this.employee = employee;
		this.leaveType = leaveType;
		this.balance = balance;
	}

	// Default constructor
	public LeaveBalance() {
	}
}
