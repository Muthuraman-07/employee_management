package com.cognizant.employeemanagement.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LeaveDto {
	@NotNull(message = "Start Date cannot be null")
	@FutureOrPresent(message = "Start Date must be in the present or future")
	private LocalDateTime startDate;

	@NotNull(message = "End Date cannot be null")
	@Future(message = "End Date must be in the future")
	private LocalDateTime endDate;

	@NotNull(message = "Leave Type cannot be null")
	@Size(min = 2, max = 30, message = "Leave Type must be between 2 and 30 characters")
	private String leaveType;
}