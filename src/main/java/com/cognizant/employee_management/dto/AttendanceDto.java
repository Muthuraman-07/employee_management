package com.cognizant.employee_management.dto;

import java.time.LocalDateTime;

import com.cognizant.employee_management.model.Employee;

import lombok.Data;
@Data
public class AttendanceDto {
	private int attendanceID;
    private Employee employee;
	private LocalDateTime clockInTime;
	private LocalDateTime clockOutTime;
	private float workHours;
	private int isPresent;
}
