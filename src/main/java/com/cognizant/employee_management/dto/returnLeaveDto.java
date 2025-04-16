package com.cognizant.employee_management.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class returnLeaveDto {
	private int leaveId;
	private LocalDateTime appliedDate;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String status;
	private String leaveType;
	private LocalDateTime approvedDate;
}
