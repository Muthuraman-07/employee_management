package com.cognizant.employee_management.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.repository.AttendanceRepository;
import com.cognizant.employee_management.repository.EmployeeRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {
 
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ModelMapper modelMapper;

 
    public List<AttendanceDto> getAllAttendance() {
        List<Attendance> attendances = attendanceRepository.findAll();

        return attendances.stream().map(attendance -> {
            AttendanceDto dto = new AttendanceDto();
            dto.setAttendanceID(attendance.getAttendanceID());
            dto.setEmployeeId(attendance.getEmployee().getEmployeeId()); // Map only employeeId
            dto.setClockInTime(attendance.getClockInTime());
            dto.setClockOutTime(attendance.getClockOutTime());
            dto.setWorkHours(attendance.getWorkHours());
            dto.setIsPresent(attendance.getIsPresent());
            return dto;
        }).collect(Collectors.toList());
    }

    
    @Override
	public AttendanceDto createAttendance(AttendanceDto attendanceDto)
	{
		Attendance attendance=modelMapper.map(attendanceDto,Attendance.class);
		Attendance saved=attendanceRepository.save(attendance);
		return modelMapper.map(saved, AttendanceDto.class);
	}
    
//    @Override
//    public AttendanceDto updateAttendance(int id, AttendanceDto dto) {
//        Attendance attendance = attendanceRepository.findById(id)
//            .orElseThrow(() -> new RuntimeException("Attendance not found"));
//        Employee employee = employeeRepository.findById(dto.getEmployeeId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID"));
//        attendance.setClockInTime(dto.getClockInTime());
//        attendance.setClockOutTime(dto.getClockOutTime());
//        attendance.setWorkHours(dto.getWorkHours());
//        attendance.setIsPresent(dto.getIsPresent());
//        attendance.setEmployee(employee);
// 
//        Attendance saved = attendanceRepository.save(attendance);
//        return modelMapper.map(saved, AttendanceDto.class);
//    }
    
    @Override
    public AttendanceDto updateAttendance(int attendanceId, AttendanceDto dto) {
        // Fetch the existing attendance record
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record with ID " + attendanceId + " not found"));

        // Update fields
        dto.setAttendanceID(attendance.getAttendanceID());
        dto.setEmployeeId(attendance.getEmployee().getEmployeeId()); // Map only employeeId
        dto.setClockInTime(attendance.getClockInTime());
        dto.setClockOutTime(attendance.getClockOutTime());
        dto.setWorkHours(attendance.getWorkHours());
        dto.setIsPresent(attendance.getIsPresent());

        // Save the updated entity
        Attendance updatedAttendance = attendanceRepository.save(attendance);

        // Configure explicit mapping for Attendance -> AttendanceDto
//        modelMapper.typeMap(Attendance.class, AttendanceDto.class).addMappings(mapper -> {
//            mapper.map(src -> src.getEmployee().getEmployeeId(), AttendanceDto::setEmployeeId);
//        });

        // Convert to DTO and return
        return modelMapper.map(updatedAttendance, AttendanceDto.class);
    }
 
 
//    @Override
//    public AttendanceDto patchAttendance(int id, AttendanceDto attendanceDto) {
//        Attendance attendance = attendanceRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Attendance not found"));
//     
//        if (attendanceDto.getEmployee() != null) {
//            attendance.setEmployee(attendanceDto.getEmployee());
//        }
//        if (attendanceDto.getClockInTime() != null) {
//            attendance.setClockInTime(attendanceDto.getClockInTime());
//        }
//        if (attendanceDto.getClockOutTime() != null) {
//            attendance.setClockOutTime(attendanceDto.getClockOutTime());
//        }
//        if (attendanceDto.getWorkHours()!=0.0) {
//            attendance.setWorkHours(attendanceDto.getWorkHours());
//        }
//     
//        Attendance updated = attendanceRepository.save(attendance);
//        return modelMapper.map(updated, AttendanceDto.class);
//    }
 
    @Override
    public void deleteAttendance(int id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance not found");
        }
        attendanceRepository.deleteById(id);
    }
    
    @Override
    public void calculateAndUpdateAttendance(AttendanceDto attendanceDto) {
//    	Employee employee = employeeRepository.findById(attendanceDto.getEmployeeId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID"));
    	
        // Validate clock-in and clock-out times
        if (attendanceDto.getClockInTime() == null || attendanceDto.getClockOutTime() == null) {
            throw new IllegalArgumentException("Clock-in and Clock-out times must not be null.");
        }
        

        // Calculate work hours
        Duration duration = Duration.between(attendanceDto.getClockInTime(), attendanceDto.getClockOutTime());
        float workHours = duration.toHours(); // Convert duration to hours

        // Update workHours in AttendanceDto
        attendanceDto.setWorkHours(workHours);

        // Automatically mark attendance based on work hours
        attendanceDto.setIsPresent(workHours >= 10 ? 1 : 0); // Set presence status

        // Convert DTO to Entity using ModelMapper
        Attendance attendance = modelMapper.map(attendanceDto, Attendance.class);

        // Save the entity using repository
        attendanceRepository.save(attendance);
    }


	
    
}
