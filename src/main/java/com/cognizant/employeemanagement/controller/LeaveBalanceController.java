package com.cognizant.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cognizant.employeemanagement.dto.LeaveBalanceDto;
import com.cognizant.employeemanagement.service.LeaveBalanceService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/leaveBalance")
@Slf4j
public class LeaveBalanceController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @PostMapping("/create-leave-balance")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<LeaveBalanceDto> create(@Valid @RequestBody LeaveBalanceDto dto) {
        log.info("[LEAVE-BALANCE-CONTROLLER] Creating leave balance for employee: {}", dto.getEmployee().getEmployeeId());
        try {
            LeaveBalanceDto savedLeaveBalance = leaveBalanceService.createLeaveBalance(dto);
            log.info("[LEAVE-BALANCE-CONTROLLER] Successfully created leave balance for employee: {}", dto.getEmployee().getEmployeeId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLeaveBalance);
        } catch (Exception e) {
            log.error("[LEAVE-BALANCE-CONTROLLER] Error creating leave balance for employee: {}. Error: {}", dto.getEmployee().getEmployeeId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/all-employee-leave-balances")
    public ResponseEntity<List<LeaveBalanceDto>> getAll() {
        log.info("[LEAVE-BALANCE-CONTROLLER] Fetching all leave balances");
        try {
            List<LeaveBalanceDto> leaveBalances = leaveBalanceService.getAllLeaveBalances();
            log.info("[LEAVE-BALANCE-CONTROLLER] Successfully fetched {} leave balances", leaveBalances.size());
            return ResponseEntity.ok(leaveBalances);
        } catch (Exception e) {
            log.error("[LEAVE-BALANCE-CONTROLLER] Error fetching all leave balances. Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

  

    @DeleteMapping("delete/leave-balance-for-employee/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> delete(@PathVariable int id) {
        log.info("[LEAVE-BALANCE-CONTROLLER] Deleting leave balance with ID: {}", id);
        try {
            leaveBalanceService.deleteLeaveBalance(id);
            log.info("[LEAVE-BALANCE-CONTROLLER] Successfully deleted leave balance with ID: {}", id);
            return ResponseEntity.ok("LeaveBalance deleted successfully.");
        } catch (Exception e) {
            log.error("[LEAVE-BALANCE-CONTROLLER] Error deleting leave balance with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting LeaveBalance: " + e.getMessage());
        }
    }
}
