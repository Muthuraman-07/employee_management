package com.cognizant.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employeemanagement.dto.AttendanceDto;
import com.cognizant.employeemanagement.dto.ReturnAttendanceDto;
import com.cognizant.employeemanagement.service.AttendanceService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/attendance")
@Slf4j
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/all-records")
    public ResponseEntity<List<ReturnAttendanceDto>> getAllAttendanceRecords() {
        log.info("[ATTENDANCE-CONTROLLER] Fetching all attendance records");
        try {
            List<ReturnAttendanceDto> attendanceList = attendanceService.getAllAttendance();
            log.info("[ATTENDANCE-CONTROLLER] Successfully retrieved {} attendance records", attendanceList.size());
            return ResponseEntity.ok(attendanceList);
        } catch (Exception e) {
            log.error("[ATTENDANCE-CONTROLLER] Error retrieving attendance records: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @PostMapping("/AttendanceRecords/{employeeId}")
    public ResponseEntity<String> markAttendance(@PathVariable int employeeId, @RequestBody AttendanceDto attendanceDto) {
        log.info("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Marking attendance", employeeId);
        try {
            if (!attendanceDto.clockOutTime.isAfter(attendanceDto.clockInTime)) {
                throw new IllegalArgumentException("Clock-out time must be after clock-in time.");
            }
            attendanceService.saveAttendance(employeeId, attendanceDto);
            log.info("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Attendance marked successfully", employeeId);
            return ResponseEntity.ok("Attendance marked successfully.");
        } catch (Exception e) {
            log.error("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Error marking attendance: {}", employeeId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error marking attendance: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteEmployeeAttendanceRecord/{attendanceId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> deleteAttendanceRecord(@PathVariable int attendanceId) {
        log.info("[ATTENDANCE-CONTROLLER] Deleting attendance record with ID: {}", attendanceId);
        try {
            attendanceService.deleteAttendance(attendanceId);
            log.info("[ATTENDANCE-CONTROLLER] Successfully deleted attendance record with ID: {}", attendanceId);
            return ResponseEntity.ok("Deleted successfully.");
        } catch (Exception e) {
            log.error("[ATTENDANCE-CONTROLLER] Error deleting attendance record with ID: {}: {}", attendanceId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting attendance: " + e.getMessage());
        }
    }
}
