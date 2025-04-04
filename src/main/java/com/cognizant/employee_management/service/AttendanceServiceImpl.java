package com.cognizant.employee_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.repository.AttendanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {
 
    @Autowired
    private AttendanceRepository attendanceRepository;
 
    @Override
    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }
 
    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
// 
//    @Override
//    public Optional<Attendance> getAttendanceById(int id) {
//        return attendanceRepository.findById(id);
//    }
 
//    @Override
//    public void deleteAttendance(int id) {
//        attendanceRepository.deleteById(id);
//    }
}
