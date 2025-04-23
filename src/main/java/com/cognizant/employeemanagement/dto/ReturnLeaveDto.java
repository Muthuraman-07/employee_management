package com.cognizant.employeemanagement.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReturnLeaveDto {
	private int leaveId;
	private int employeeId;
	private LocalDateTime appliedDate;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String status;
	private String leaveType;
	private LocalDateTime approvedDate;
}
