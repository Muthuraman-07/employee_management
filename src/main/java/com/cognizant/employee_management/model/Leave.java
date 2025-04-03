package com.cognizant.employee_management.model;
import java.util.Date;
import lombok.Data;
@Data
public class Leave {
	private int leaveId;
	private int employeeId;
	private Date appliedDate;
	private String leaveType;
	private Date startDate;
	private Date endDate;
	private String status;
	private Date approvedDate;
}
