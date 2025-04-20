package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.dto.returnLeaveDto;
import com.cognizant.employee_management.service.LeaveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private static final Logger log = LoggerFactory.getLogger(LeaveController.class);

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/all")
    public ResponseEntity<List<LeaveDto>> getAllLeaves() {
        log.info("[LEAVE-CONTROLLER] Fetching all leaves");
        try {
            List<LeaveDto> leaves = leaveService.getAllLeaves();
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

    @GetMapping("/{id}")
    public ResponseEntity<LeaveDto> getLeaveById(@PathVariable int id) {
        log.info("[LEAVE-CONTROLLER] Fetching leave by ID: {}", id);
        try {
            LeaveDto leave = leaveService.getLeaveById(id);
            log.info("[LEAVE-CONTROLLER] Successfully fetched leave with ID: {}", id);
            return ResponseEntity.ok(leave);
        } catch (Exception e) {
            log.error("[LEAVE-CONTROLLER] Error fetching leave by ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/createLeave")
    public ResponseEntity<LeaveDto> createLeave(@Valid @RequestBody LeaveDto dto) {
        log.info("[LEAVE-CONTROLLER] Creating leave for employee ID: {}", dto.getEmployeeId());
        try {
            LeaveDto createdLeave = leaveService.createLeave(dto);
            log.info("[LEAVE-CONTROLLER] Successfully created leave for employee ID: {}", dto.getEmployeeId());
            return ResponseEntity.ok(createdLeave);
        } catch (Exception e) {
            log.error("[LEAVE-CONTROLLER] Error creating leave for employee ID: {}. Error: {}", dto.getEmployeeId(), e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<LeaveDto> updateLeave(@PathVariable int id, @Valid @RequestBody LeaveDto dto) {
        log.info("[LEAVE-CONTROLLER] Updating leave with ID: {}", id);
        try {
            LeaveDto updatedLeave = leaveService.updateLeave(id, dto);
            log.info("[LEAVE-CONTROLLER] Successfully updated leave with ID: {}", id);
            return ResponseEntity.ok(updatedLeave);
        } catch (Exception e) {
            log.error("[LEAVE-CONTROLLER] Error updating leave with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(404).body(null);
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
            leaveService.applyLeave(id, leaveDto);
            log.info("[LEAVE-CONTROLLER] Leave successfully applied for employee ID: {}", id);
            return ResponseEntity.ok("Leave applied successfully");
        } catch (Exception e) {
            log.error("[LEAVE-CONTROLLER] Error applying leave for employee ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error applying leave");
        }
    }
}


//package com.cognizant.employee_management.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import com.cognizant.employee_management.dto.LeaveDto;
//import com.cognizant.employee_management.dto.returnLeaveDto;
//import com.cognizant.employee_management.service.LeaveService;
//
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequestMapping("/api/leave")
//@Slf4j // Lombok annotation for logging
//public class LeaveController {
//
//    @Autowired
//    private LeaveService leaveService;
//
//    @GetMapping("/all")
//    public ResponseEntity<List<LeaveDto>> getAllLeaves() {
//        log.info("[LEAVE-CONTROLLER] Fetching all leave records");
//        try {
//            List<LeaveDto> leaves = leaveService.getAllLeaves();
//            log.info("[LEAVE-CONTROLLER] Successfully fetched {} leave records", leaves.size());
//            return ResponseEntity.ok(leaves);
//        } catch (Exception e) {
//            log.error("[LEAVE-CONTROLLER] Error fetching all leave records: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @GetMapping("/leaveRequestStatus/{status}")
//    public ResponseEntity<List<returnLeaveDto>> getAllPendingLeaveRequests(@PathVariable String status) {
//        log.info("[LEAVE-CONTROLLER] Fetching leave requests with status: {}", status);
//        try {
//            List<returnLeaveDto> pendingLeaves = leaveService.getAllPendingLeaveRequests(status);
//            log.info("[LEAVE-CONTROLLER] Successfully fetched {} leave requests with status: {}", pendingLeaves.size(), status);
//            return ResponseEntity.ok(pendingLeaves);
//        } catch (Exception e) {
//            log.error("[LEAVE-CONTROLLER] Error fetching leave requests with status: {}. Error: {}", status, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<LeaveDto> getLeaveById(@PathVariable int id) {
//        log.info("[LEAVE-CONTROLLER] Fetching leave record with ID: {}", id);
//        try {
//            LeaveDto leave = leaveService.getLeaveById(id);
//            log.info("[LEAVE-CONTROLLER] Successfully fetched leave record with ID: {}", id);
//            return ResponseEntity.ok(leave);
//        } catch (Exception e) {
//            log.error("[LEAVE-CONTROLLER] Error fetching leave record with ID: {}. Error: {}", id, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//    @PostMapping("/createLeave")
//    public ResponseEntity<LeaveDto> createLeave(@Valid @RequestBody LeaveDto dto) {
//        log.info("[LEAVE-CONTROLLER] Creating leave for employee: {}", dto.getEmployee().getEmployeeId());
//        try {
//            LeaveDto createdLeave = leaveService.createLeave(dto);
//            log.info("[LEAVE-CONTROLLER] Successfully created leave for employee: {}", dto.getEmployee().getEmployeeId());
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdLeave);
//        } catch (Exception e) {
//            log.error("[LEAVE-CONTROLLER] Error creating leave for employee: {}. Error: {}", dto.getEmployee().getEmployeeId(), e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @PutMapping("update/{id}")
//    public ResponseEntity<LeaveDto> updateLeave(@PathVariable int id, @Valid @RequestBody LeaveDto dto) {
//        log.info("[LEAVE-CONTROLLER][Employee-ID: {}] Updating leave record with ID: {}", dto.getEmployee().getEmployeeId(), id);
//        try {
//            LeaveDto updatedLeave = leaveService.updateLeave(id, dto);
//            log.info("[LEAVE-CONTROLLER][Employee-ID: {}] Successfully updated leave record with ID: {}", dto.getEmployee().getEmployeeId(), id);
//            return ResponseEntity.ok(updatedLeave);
//        } catch (Exception e) {
//            log.error("[LEAVE-CONTROLLER][Employee-ID: {}] Error updating leave record with ID: {}. Error: {}", dto.getEmployee().getEmployeeId(), id, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @DeleteMapping("deleteLeave/{id}")
//    public ResponseEntity<String> deleteLeave(@PathVariable int id) {
//        log.info("[LEAVE-CONTROLLER] Deleting leave record with ID: {}", id);
//        try {
//            leaveService.deleteLeave(id);
//            log.info("[LEAVE-CONTROLLER] Successfully deleted leave record with ID: {}", id);
//            return ResponseEntity.ok("Leave deleted successfully.");
//        } catch (Exception e) {
//            log.error("[LEAVE-CONTROLLER] Error deleting leave record with ID: {}. Error: {}", id, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error deleting leave record: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("applyLeave/{id}")
//    public ResponseEntity<String> applyLeave(@PathVariable int id, @RequestBody LeaveDto leaveDto) {
//        log.info("[LEAVE-CONTROLLER][Employee-ID: {}] Applying leave with ID: {}", leaveDto.getEmployee().getEmployeeId(), id);
//        try {
//            leaveService.applyLeave(id, leaveDto);
//            log.info("[LEAVE-CONTROLLER][Employee-ID: {}] Leave successfully applied with ID: {}", leaveDto.getEmployee().getEmployeeId(), id);
//            return ResponseEntity.ok("Leave Applied");
//        } catch (Exception e) {
//            log.error("[LEAVE-CONTROLLER][Employee-ID: {}] Error applying leave with ID: {}. Error: {}", leaveDto.getEmployee().getEmployeeId(), id, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error applying leave: " + e.getMessage());
//        }
//    }
//}

