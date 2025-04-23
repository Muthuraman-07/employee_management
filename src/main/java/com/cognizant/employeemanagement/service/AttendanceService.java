package com.cognizant.employeemanagement.service;

import java.util.List;

import com.cognizant.employeemanagement.dto.AttendanceDto;
import com.cognizant.employeemanagement.dto.ReturnAttendanceDto;

public interface AttendanceService {
	List<ReturnAttendanceDto> getAllAttendance();

	AttendanceDto saveAttendance(int id, AttendanceDto attendanceDto);

	void deleteAttendance(int id);
}
