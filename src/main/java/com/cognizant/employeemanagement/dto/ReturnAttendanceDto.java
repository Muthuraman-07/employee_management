package com.cognizant.employeemanagement.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReturnAttendanceDto {
	
	private int attendanceID;
    private int employeeId;
	private LocalDateTime clockInTime;
	private LocalDateTime clockOutTime;
	private float workHours;
	private int isPresent;
}

