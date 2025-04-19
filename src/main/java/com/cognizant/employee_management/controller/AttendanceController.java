package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.service.AttendanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@GetMapping("/all")
	public ResponseEntity<List<AttendanceDto>> getAllAttendance() {
		List<AttendanceDto> attendanceList = attendanceService.getAllAttendance();
		return ResponseEntity.ok(attendanceList);
	}

//	@PostMapping("/saveAttendance")
//	public ResponseEntity<AttendanceDto> createAttedance(@Valid @RequestBody AttendanceDto attendanceDto) {
//		AttendanceDto saved = attendanceService.createAttendance(attendanceDto);
//		return new ResponseEntity<>(saved, HttpStatus.OK);
//
//	}
	
	@PostMapping("/apply")
    public String updateAttendance(@RequestBody AttendanceDto attendanceDto) {
        try {
            attendanceService.calculateAndUpdateAttendance(attendanceDto);
            return "Attendance updated successfully.";
        } catch (Exception e) {
            return "Error updating attendance: " + e.getMessage();
        }
    }

	@PatchMapping("/{id}")
	public ResponseEntity<AttendanceDto> updateAttendance(@PathVariable int id,
			@Valid @RequestBody AttendanceDto attendanceDto) {
		return ResponseEntity.ok(attendanceService.updateAttendance(id, attendanceDto));
	}


//	@PatchMapping("/patchAttendance/{id}")
//	public ResponseEntity<AttendanceDto> patchAttendance(@PathVariable int id,
//			@Valid @RequestBody AttendanceDto attendanceDto) {
//		AttendanceDto updated = attendanceService.patchAttendance(id, attendanceDto);
//		return ResponseEntity.ok(updated);
//	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAttendance(@PathVariable int id) {
		attendanceService.deleteAttendance(id);
		return ResponseEntity.ok("Deleted successfully.");
	}
}
