package com.cognizant.employee_management.dto;
 
import com.cognizant.employee_management.model.Employee;

import lombok.Data;
 
@Data
public class ShiftRequestDto {
	   private int id;
	    private Employee employee;
	    private int requestedShiftId;
	    private String status;
	    private boolean approvedByManager;        // Status of the request (Pending, Submitted to Manager, Approved, Rejected)
}