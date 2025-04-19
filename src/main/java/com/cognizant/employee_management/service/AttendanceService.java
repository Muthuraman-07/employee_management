package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.dto.AttendanceDto;

public interface AttendanceService {
    List<AttendanceDto> getAllAttendance();

//	Attendance saveAttendance(Attendance attendance);

	AttendanceDto createAttendance(AttendanceDto attendanceDto);
	AttendanceDto updateAttendance(int id, AttendanceDto dto);
	 
//    AttendanceDto patchAttendance(int id,AttendanceDto attendanceDto);
 
    void deleteAttendance(int id);
    
    void calculateAndUpdateAttendance(AttendanceDto attendanceDto);
    
    
}
