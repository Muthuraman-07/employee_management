package com.cognizant.employee_management.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.repository.AttendanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger log = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AttendanceDto> getAllAttendance() {
        log.info("[ATTENDANCE-SERVICE] Fetching all attendance records");
        try {
            List<Attendance> attendances = attendanceRepository.findAll();
            log.info("[ATTENDANCE-SERVICE] Successfully fetched {} attendance records", attendances.size());

            return attendances.stream().map(attendance -> {
                AttendanceDto dto = new AttendanceDto();
                dto.setAttendanceID(attendance.getAttendanceID());
                dto.setEmployeeId(attendance.getEmployee().getEmployeeId());
                dto.setClockInTime(attendance.getClockInTime());
                dto.setClockOutTime(attendance.getClockOutTime());
                dto.setWorkHours(attendance.getWorkHours());
                dto.setIsPresent(attendance.getIsPresent());
                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[ATTENDANCE-SERVICE] Error fetching attendance records: {}", e.getMessage(), e);
            throw e; // Re-throw exception for handling at a higher level if needed
        }
    }

    @Override
    public AttendanceDto createAttendance(AttendanceDto attendanceDto) {
        log.info("[ATTENDANCE-SERVICE] Creating attendance record for employee ID: {}", attendanceDto.getEmployeeId());
        try {
            Attendance attendance = modelMapper.map(attendanceDto, Attendance.class);
            Attendance saved = attendanceRepository.save(attendance);
            log.info("[ATTENDANCE-SERVICE] Attendance record created successfully for employee ID: {}", attendanceDto.getEmployeeId());
            return modelMapper.map(saved, AttendanceDto.class);
        } catch (Exception e) {
            log.error("[ATTENDANCE-SERVICE] Error creating attendance record for employee ID: {}. Error: {}", attendanceDto.getEmployeeId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public AttendanceDto updateAttendance(int attendanceId, AttendanceDto dto) {
        log.info("[ATTENDANCE-SERVICE] Updating attendance record with ID: {}", attendanceId);
        try {
            Attendance attendance = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() -> {
                        log.warn("[ATTENDANCE-SERVICE] Attendance record with ID: {} not found", attendanceId);
                        return new RuntimeException("Attendance record with ID " + attendanceId + " not found");
                    });

            attendance.setClockInTime(dto.getClockInTime());
            attendance.setClockOutTime(dto.getClockOutTime());
            attendance.setWorkHours(dto.getWorkHours());
            attendance.setIsPresent(dto.getIsPresent());

            Attendance updatedAttendance = attendanceRepository.save(attendance);
            log.info("[ATTENDANCE-SERVICE] Attendance record with ID: {} updated successfully", attendanceId);

            return modelMapper.map(updatedAttendance, AttendanceDto.class);
        } catch (Exception e) {
            log.error("[ATTENDANCE-SERVICE] Error updating attendance record with ID: {}. Error: {}", attendanceId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteAttendance(int id) {
        log.info("[ATTENDANCE-SERVICE] Deleting attendance record with ID: {}", id);
        try {
            if (!attendanceRepository.existsById(id)) {
                log.warn("[ATTENDANCE-SERVICE] Attendance record with ID: {} not found", id);
                throw new RuntimeException("Attendance not found");
            }
            attendanceRepository.deleteById(id);
            log.info("[ATTENDANCE-SERVICE] Attendance record with ID: {} deleted successfully", id);
        } catch (Exception e) {
            log.error("[ATTENDANCE-SERVICE] Error deleting attendance record with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void calculateAndUpdateAttendance(AttendanceDto attendanceDto) {
        log.info("[ATTENDANCE-SERVICE] Calculating and updating attendance for employee ID: {}", attendanceDto.getEmployeeId());
        try {
            if (attendanceDto.getClockInTime() == null || attendanceDto.getClockOutTime() == null) {
                log.warn("[ATTENDANCE-SERVICE] Clock-in or Clock-out times are null for employee ID: {}", attendanceDto.getEmployeeId());
                throw new IllegalArgumentException("Clock-in and Clock-out times must not be null.");
            }

            Duration duration = Duration.between(attendanceDto.getClockInTime(), attendanceDto.getClockOutTime());
            float workHours = duration.toHours();

            attendanceDto.setWorkHours(workHours);
            attendanceDto.setIsPresent(workHours >= 10 ? 1 : 0);

            Attendance attendance = modelMapper.map(attendanceDto, Attendance.class);
            attendanceRepository.save(attendance);

            log.info("[ATTENDANCE-SERVICE] Attendance record updated successfully for employee ID: {}", attendanceDto.getEmployeeId());
        } catch (Exception e) {
            log.error("[ATTENDANCE-SERVICE] Error calculating and updating attendance for employee ID: {}. Error: {}", attendanceDto.getEmployeeId(), e.getMessage(), e);
            throw e;
        }
    }
}
