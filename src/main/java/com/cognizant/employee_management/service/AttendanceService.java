package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.model.Attendance;

public interface AttendanceService {
    List<Attendance> getAllAttendance();

	Attendance saveAttendance(Attendance attendance);
}
