package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.dto.ReturnAttendanceDto;

public interface AttendanceService {
	List<ReturnAttendanceDto> getAllAttendance();

	AttendanceDto saveAttendance(int id, AttendanceDto attendanceDto);

	void deleteAttendance(int id);
}
