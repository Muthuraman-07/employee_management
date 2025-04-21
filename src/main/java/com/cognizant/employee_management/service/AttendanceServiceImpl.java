package com.cognizant.employee_management.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.dto.ReturnAttendanceDto;
import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.repository.AttendanceRepository;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
	public List<ReturnAttendanceDto> getAllAttendance() {
    	logger.info("[ATTENDANCE-SERVICE] Fetching all attendance records");
		try {
			List<Attendance> attendances = attendanceRepository.findAll();
			logger.info("[ATTENDANCE-SERVICE] Successfully fetched {} attendance records", attendances.size());
 
			return attendances.stream().map(attendance -> {
				ReturnAttendanceDto dto = new ReturnAttendanceDto();
				dto.setAttendanceID(attendance.getAttendanceID());
				dto.setEmployeeId(attendance.getEmployee().getEmployeeId());
				dto.setClockInTime(attendance.getClockInTime());
				dto.setClockOutTime(attendance.getClockOutTime());
				dto.setWorkHours(attendance.getWorkHours());
				dto.setIsPresent(attendance.getIsPresent());
				return dto;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("[ATTENDANCE-SERVICE] Error fetching attendance records: {}", e.getMessage(), e);
			throw e;
		}
	}
 

    @Override
    public AttendanceDto saveAttendance(int id, AttendanceDto attendanceDto) {
        logger.info("Saving attendance for employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found");
                });

        Attendance attendance = modelMapper.map(attendanceDto, Attendance.class);
        attendance.setEmployee(employee);

        Duration duration = Duration.between(attendanceDto.getClockInTime(), attendanceDto.getClockOutTime());

        LocalDateTime clockInTime = attendanceDto.getClockInTime();
        LocalDateTime clockOutTime = attendanceDto.getClockOutTime();

        if (clockInTime.toLocalTime().isBefore(employee.getShift().getShiftStartTime())
                && clockOutTime.toLocalTime().isAfter(employee.getShift().getShiftEndTime())
                && duration.toHours() >= 10) {
            attendance.setWorkHours(duration.toHours());
            attendance.setIsPresent(1);
            logger.info("Attendance marked as present for employee with ID: {}", id);
        } else {
            attendance.setWorkHours(0);
            attendance.setIsPresent(0);
            logger.info("Attendance marked as absent for employee with ID: {}", id);
        }

        Attendance saved = attendanceRepository.save(attendance);
        logger.info("Attendance saved successfully for employee with ID: {}", id);
        return modelMapper.map(saved, AttendanceDto.class);
    }

    @Override
    public void deleteAttendance(int id) {
        logger.info("Deleting attendance record with ID: {}", id);
        if (!attendanceRepository.existsById(id)) {
            logger.error("Attendance not found with ID: {}", id);
            throw new RuntimeException("Attendance not found");
        }
        attendanceRepository.deleteById(id);
        logger.info("Attendance record deleted successfully with ID: {}", id);
    }
}
