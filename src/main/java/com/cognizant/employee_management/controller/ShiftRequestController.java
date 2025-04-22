package com.cognizant.employee_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.ReturnSwapRequestDTO;
import com.cognizant.employee_management.dto.ShiftRequestDto;
import com.cognizant.employee_management.service.ShiftService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/shifts")
@Slf4j 
public class ShiftRequestController {

    @Autowired
    private ShiftService shiftService;

    @PostMapping("/request-swap")
    public ResponseEntity<ReturnSwapRequestDTO> requestShiftSwap(@RequestParam int employeeId, @RequestParam int shiftId) {
        log.info("[SHIFT-CONTROLLER] Requesting shift swap for employee ID: {} and shift ID: {}", employeeId, shiftId);
        try {
        	ReturnSwapRequestDTO shiftRequest = shiftService.requestShiftSwap(employeeId, shiftId);
            log.info("[SHIFT-CONTROLLER] Successfully requested shift swap with ID: {}", shiftRequest.getId());
            return new ResponseEntity<>(shiftRequest, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("[SHIFT-CONTROLLER] Error requesting shift swap: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/approve-swap")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ReturnSwapRequestDTO> approveShiftSwap(@RequestParam int requestId, @RequestParam boolean approved) {
        log.info("[SHIFT-CONTROLLER] Approving shift swap request ID: {}", requestId);
        try {
        	ReturnSwapRequestDTO shiftRequest = shiftService.approveShiftSwap(requestId, approved);
            log.info("[SHIFT-CONTROLLER] Successfully approved shift swap request with ID: {}", shiftRequest.getId());
            return new ResponseEntity<>(shiftRequest, HttpStatus.OK);
        } catch (Exception e) {
            log.error("[SHIFT-CONTROLLER] Error approving shift swap request: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
