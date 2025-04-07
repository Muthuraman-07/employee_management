package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.model.Attendance;

public interface AttendanceService {
    List<Attendance> getAllAttendance();

//	Attendance saveAttendance(Attendance attendance);

	AttendanceDto createAttendance(AttendanceDto attendanceDto);
	AttendanceDto updateAttendance(int id, AttendanceDto dto);
	 
    AttendanceDto patchAttendance(int id,AttendanceDto attendanceDto);
 
    void deleteAttendance(int id);
}
