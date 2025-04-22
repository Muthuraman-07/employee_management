package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.dto.ReturnAttendanceDto;
import com.cognizant.employee_management.service.AttendanceService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/attendance")
@Slf4j
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/all")
    public ResponseEntity<List<ReturnAttendanceDto>> getAllAttendance() {
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

    @PostMapping("/mark/{id}")
    public ResponseEntity<String> saveAttendance(@PathVariable int id, @RequestBody AttendanceDto attendanceDto) {
        log.info("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Applying attendance update", id);
        try {
        	if(!attendanceDto.clockOutTime.isAfter(attendanceDto.clockInTime))
        	{
        		throw new IllegalArgumentException("Clock-out time must be after clock-in time.");
        	}
            attendanceService.saveAttendance(id,attendanceDto);
            log.info("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Attendance update successful", id);
            return ResponseEntity.ok("Attendance updated successfully.");
        } catch (Exception e) {
            log.error("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Error updating attendance: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error updating attendance: " + e.getMessage());
        }
    }

   

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable int id) {
        log.info("[ATTENDANCE-CONTROLLER] Deleting attendance record with ID: {}", id);
        try {
            attendanceService.deleteAttendance(id);
            log.info("[ATTENDANCE-CONTROLLER] Successfully deleted attendance record with ID: {}", id);
            return ResponseEntity.ok("Deleted successfully.");
        } catch (Exception e) {
            log.error("[ATTENDANCE-CONTROLLER] Error deleting attendance record with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting attendance: " + e.getMessage());
        }
    }
}
