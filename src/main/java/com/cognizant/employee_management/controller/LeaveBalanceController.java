package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cognizant.employee_management.dto.LeaveBalanceDto;
import com.cognizant.employee_management.service.LeaveBalanceService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/leavebalance")
@Slf4j // Lombok annotation for logging
public class LeaveBalanceController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @PostMapping
    public ResponseEntity<LeaveBalanceDto> create(@Valid @RequestBody LeaveBalanceDto dto) {
        log.info("[LEAVE-BALANCE-CONTROLLER] Creating leave balance for employee: {}", dto.getEmployee().getEmployeeId());
        try {
            LeaveBalanceDto savedLeaveBalance = leaveBalanceService.createLeaveBalance(dto);
            log.info("[LEAVE-BALANCE-CONTROLLER] Successfully created leave balance for employee: {}", dto.getEmployee().getEmployeeId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLeaveBalance);
        } catch (Exception e) {
            log.error("[LEAVE-BALANCE-CONTROLLER] Error creating leave balance for employee: {}. Error: {}", dto.getEmployee().getEmployeeId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveBalanceDto> getById(@PathVariable int id) {
        log.info("[LEAVE-BALANCE-CONTROLLER] Fetching leave balance with ID: {}", id);
        try {
            LeaveBalanceDto leaveBalance = leaveBalanceService.getLeaveBalanceById(id);
            log.info("[LEAVE-BALANCE-CONTROLLER] Successfully fetched leave balance with ID: {}", id);
            return ResponseEntity.ok(leaveBalance);
        } catch (Exception e) {
            log.error("[LEAVE-BALANCE-CONTROLLER] Error fetching leave balance with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LeaveBalanceDto>> getAll() {
        log.info("[LEAVE-BALANCE-CONTROLLER] Fetching all leave balances");
        try {
            List<LeaveBalanceDto> leaveBalances = leaveBalanceService.getAllLeaveBalances();
            log.info("[LEAVE-BALANCE-CONTROLLER] Successfully fetched {} leave balances", leaveBalances.size());
            return ResponseEntity.ok(leaveBalances);
        } catch (Exception e) {
            log.error("[LEAVE-BALANCE-CONTROLLER] Error fetching all leave balances. Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveBalanceDto> update(@PathVariable int id, @Valid @RequestBody LeaveBalanceDto dto) {
        log.info("[LEAVE-BALANCE-CONTROLLER][Employee-ID: {}] Updating leave balance with ID: {}", dto.getEmployee().getEmployeeId(), id);
        try {
            LeaveBalanceDto updatedLeaveBalance = leaveBalanceService.updateLeaveBalance(id, dto);
            log.info("[LEAVE-BALANCE-CONTROLLER][Employee-ID: {}] Successfully updated leave balance with ID: {}", dto.getEmployee().getEmployeeId(), id);
            return ResponseEntity.ok(updatedLeaveBalance);
        } catch (Exception e) {
            log.error("[LEAVE-BALANCE-CONTROLLER][Employee-ID: {}] Error updating leave balance with ID: {}. Error: {}", dto.getEmployee().getEmployeeId(), id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        log.info("[LEAVE-BALANCE-CONTROLLER] Deleting leave balance with ID: {}", id);
        try {
            leaveBalanceService.deleteLeaveBalance(id);
            log.info("[LEAVE-BALANCE-CONTROLLER] Successfully deleted leave balance with ID: {}", id);
            return ResponseEntity.ok("LeaveBalance deleted successfully.");
        } catch (Exception e) {
            log.error("[LEAVE-BALANCE-CONTROLLER] Error deleting leave balance with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting LeaveBalance: " + e.getMessage());
        }
    }
}
