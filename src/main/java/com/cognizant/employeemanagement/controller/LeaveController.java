package com.cognizant.employeemanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cognizant.employeemanagement.dto.LeaveDto;
import com.cognizant.employeemanagement.dto.ReturnLeaveDto;
import com.cognizant.employeemanagement.model.Leave;
import com.cognizant.employeemanagement.service.LeaveService;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

	private static final Logger log = LoggerFactory.getLogger(LeaveController.class);

	@Autowired
	private LeaveService leaveService;

	@GetMapping("/all-leaves-history")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<List<ReturnLeaveDto>> getAllLeaves() {
		log.info("[LEAVE-CONTROLLER] Fetching all leaves");
		try {
			List<ReturnLeaveDto> leaves = leaveService.getAllLeaves();
			log.info("[LEAVE-CONTROLLER] Successfully fetched {} leaves", leaves.size());
			return ResponseEntity.ok(leaves);
		} catch (Exception e) {
			log.error("[LEAVE-CONTROLLER] Error fetching all leaves. Error: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/leave-history-by-status/{status}")
	public ResponseEntity<List<ReturnLeaveDto>> getAllPendingLeaveRequests(@PathVariable String status) {
		log.info("[LEAVE-CONTROLLER] Fetching leave requests with status: {}", status);
		try {
			List<ReturnLeaveDto> pendingRequests = leaveService.getAllPendingLeaveRequests(status);
			log.info("[LEAVE-CONTROLLER] Successfully fetched {} leave requests with status: {}",
					pendingRequests.size(), status);
			return ResponseEntity.ok(pendingRequests);
		} catch (Exception e) {
			log.error("[LEAVE-CONTROLLER] Error fetching leave requests with status: {}. Error: {}", status,
					e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("/delete-leave-record/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> deleteLeave(@PathVariable int id) {
		log.info("[LEAVE-CONTROLLER] Deleting leave with ID: {}", id);
		try {
			leaveService.deleteLeave(id);
			log.info("[LEAVE-CONTROLLER] Leave with ID: {} deleted successfully", id);
			return ResponseEntity.ok("Leave deleted successfully");
		} catch (Exception e) {
			log.error("[LEAVE-CONTROLLER] Error deleting leave with ID: {}. Error: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting leave");
		}
	}

	@PostMapping("apply-leave/{employeeId}")
	public ResponseEntity<String> applyLeave(@PathVariable int employeeId, @RequestBody LeaveDto leaveDto) {
		log.info("[LEAVE-CONTROLLER] Applying leave for employee ID: {}", employeeId);
		try {
			Leave leave = leaveService.applyLeave(employeeId, leaveDto); // Updated service method to return Leave object
			log.info("[LEAVE-CONTROLLER] Leave successfully applied for employee ID: {}", employeeId);
			return ResponseEntity.ok("Leave applied successfully. Status: " + leave.getStatus());
		} catch (RuntimeException e) {
			log.error("[LEAVE-CONTROLLER] Error applying leave for employee ID: {}. Error: {}", employeeId, e.getMessage(), e);
			return ResponseEntity.status(400).body("Error applying leave: " + e.getMessage());
		} catch (Exception e) {
			log.error("[LEAVE-CONTROLLER] Unexpected error applying leave for employee ID: {}. Error: {}", employeeId,
					e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected error applying leave");
		}
	}
}
