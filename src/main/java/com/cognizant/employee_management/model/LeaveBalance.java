package com.cognizant.employee_management.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class LeaveBalance {
	private int EmployeeID;
	private String LeaveType;
	private int Balance;
	
	private Employee employee;
}
