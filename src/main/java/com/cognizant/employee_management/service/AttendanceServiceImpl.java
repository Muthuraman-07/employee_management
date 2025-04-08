package com.cognizant.employee_management.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.repository.AttendanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {
 
    @Autowired
    private AttendanceRepository attendanceRepository;
 
    
    @Autowired
    private ModelMapper modelMapper;

 
    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    
    @Override
	public AttendanceDto createAttendance(AttendanceDto attendanceDto)
	{
		Attendance attendance=modelMapper.map(attendanceDto,Attendance.class);
		Attendance saved=attendanceRepository.save(attendance);
		return modelMapper.map(saved, AttendanceDto.class);
	}
    
    @Override
    public AttendanceDto updateAttendance(int id, AttendanceDto dto) {
        Attendance attendance = attendanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Attendance not found"));
 
        attendance.setClockInTime(dto.getClockInTime());
        attendance.setClockOutTime(dto.getClockOutTime());
        attendance.setWorkHours(dto.getWorkHours());
        attendance.setIsPresent(dto.getIsPresent());
        attendance.setEmployee(dto.getEmployee());
 
        Attendance saved = attendanceRepository.save(attendance);
        return modelMapper.map(saved, AttendanceDto.class);
    }
 
    @Override
    public AttendanceDto patchAttendance(int id, AttendanceDto attendanceDto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
     
        if (attendanceDto.getEmployee() != null) {
            attendance.setEmployee(attendanceDto.getEmployee());
        }
        if (attendanceDto.getClockInTime() != null) {
            attendance.setClockInTime(attendanceDto.getClockInTime());
        }
        if (attendanceDto.getClockOutTime() != null) {
            attendance.setClockOutTime(attendanceDto.getClockOutTime());
        }
        if (attendanceDto.getWorkHours()!=0.0) {
            attendance.setWorkHours(attendanceDto.getWorkHours());
        }
     
        Attendance updated = attendanceRepository.save(attendance);
        return modelMapper.map(updated, AttendanceDto.class);
    }
 
    @Override
    public void deleteAttendance(int id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance not found");
        }
        attendanceRepository.deleteById(id);
    }
}
