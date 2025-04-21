package com.cognizant.employee_management.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.dto.returnLeaveDto;
import com.cognizant.employee_management.model.Leave;
import com.cognizant.employee_management.service.LeaveService;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private static final Logger log = LoggerFactory.getLogger(LeaveController.class);

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/all")
    public ResponseEntity<List<returnLeaveDto>> getAllLeaves() {
        log.info("[LEAVE-CONTROLLER] Fetching all leaves");
        try {
            List<returnLeaveDto> leaves = leaveService.getAllLeaves();
            log.info("[LEAVE-CONTROLLER] Successfully fetched {} leaves", leaves.size());
            return ResponseEntity.ok(leaves);
        } catch (Exception e) {
            log.error("[LEAVE-CONTROLLER] Error fetching all leaves. Error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/leaveRequestStatus/{status}")
    public ResponseEntity<List<returnLeaveDto>> getAllPendingLeaveRequests(@PathVariable String status) {
        log.info("[LEAVE-CONTROLLER] Fetching leave requests with status: {}", status);
        try {
            List<returnLeaveDto> pendingRequests = leaveService.getAllPendingLeaveRequests(status);
            log.info("[LEAVE-CONTROLLER] Successfully fetched {} leave requests with status: {}", pendingRequests.size(), status);
            return ResponseEntity.ok(pendingRequests);
        } catch (Exception e) {
            log.error("[LEAVE-CONTROLLER] Error fetching leave requests with status: {}. Error: {}", status, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

   

    @DeleteMapping("deleteLeave/{id}")
    public ResponseEntity<String> deleteLeave(@PathVariable int id) {
        log.info("[LEAVE-CONTROLLER] Deleting leave with ID: {}", id);
        try {
            leaveService.deleteLeave(id);
            log.info("[LEAVE-CONTROLLER] Leave with ID: {} deleted successfully", id);
            return ResponseEntity.ok("Leave deleted successfully");
        } catch (Exception e) {
            log.error("[LEAVE-CONTROLLER] Error deleting leave with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(404).body("Error deleting leave");
        }
    }

    @PostMapping("applyLeave/{id}")
    public ResponseEntity<String> applyLeave(@PathVariable int id, @RequestBody LeaveDto leaveDto) {
        log.info("[LEAVE-CONTROLLER] Applying leave for employee ID: {}", id);
        try {
            Leave leave = leaveService.applyLeave(id, leaveDto); // Updated service method to return Leave object
            log.info("[LEAVE-CONTROLLER] Leave successfully applied for employee ID: {}", id);
            return ResponseEntity.ok("Leave applied successfully. Status: " + leave.getStatus());
        } catch (Exception e) {
            log.error("[LEAVE-CONTROLLER] Error applying leave for employee ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error applying leave");
        }
    }

}

