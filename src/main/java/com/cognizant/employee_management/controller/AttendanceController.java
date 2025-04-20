package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cognizant.employee_management.dto.AttendanceDto;
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
    public ResponseEntity<List<AttendanceDto>> getAllAttendance() {
        log.info("[ATTENDANCE-CONTROLLER] Fetching all attendance records");
        try {
            List<AttendanceDto> attendanceList = attendanceService.getAllAttendance();
            log.info("[ATTENDANCE-CONTROLLER] Successfully retrieved {} attendance records", attendanceList.size());
            return ResponseEntity.ok(attendanceList);
        } catch (Exception e) {
            log.error("[ATTENDANCE-CONTROLLER] Error retrieving attendance records: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/apply")
    public ResponseEntity<String> updateAttendance(@RequestBody AttendanceDto attendanceDto) {
        log.info("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Applying attendance update", attendanceDto.getEmployeeId());
        try {
            attendanceService.calculateAndUpdateAttendance(attendanceDto);
            log.info("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Attendance update successful", attendanceDto.getEmployeeId());
            return ResponseEntity.ok("Attendance updated successfully.");
        } catch (Exception e) {
            log.error("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Error updating attendance: {}", attendanceDto.getEmployeeId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating attendance: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AttendanceDto> updateAttendance(@PathVariable int id, @Valid @RequestBody AttendanceDto attendanceDto) {
        log.info("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Updating attendance record with ID: {}", attendanceDto.getEmployeeId(), id);
        try {
            AttendanceDto updatedAttendance = attendanceService.updateAttendance(id, attendanceDto);
            log.info("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Successfully updated attendance record with ID: {}", attendanceDto.getEmployeeId(), id);
            return ResponseEntity.ok(updatedAttendance);
        } catch (Exception e) {
            log.error("[ATTENDANCE-CONTROLLER][Employee-ID: {}] Error updating attendance record with ID: {}: {}", attendanceDto.getEmployeeId(), id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting attendance: " + e.getMessage());
        }
    }
}
