package com.cognizant.employee_management.controller;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import com.cognizant.employee_management.dto.ShiftSwapRequestDto;

import com.cognizant.employee_management.service.ShiftSwapRequestService;
 
import java.util.List;
 
@RestController

@RequestMapping("/api/shift-swap-requests")

public class ShiftSwapRequestController {
 
    private static final Logger log = LoggerFactory.getLogger(ShiftSwapRequestController.class);
 
    @Autowired

    private ShiftSwapRequestService shiftSwapRequestService;
 
    @PostMapping("/request")

    public ResponseEntity<ShiftSwapRequestDto> createSwapRequest(@RequestBody ShiftSwapRequestDto requestDto) {

        log.info("[SHIFT-SWAP-CONTROLLER] Creating swap request for requester ID: {} and approver ID: {}",

                requestDto.getRequesterId(), requestDto.getApproverId());

        try {

            ShiftSwapRequestDto createdRequest = shiftSwapRequestService.createShiftSwapRequest(requestDto);

            log.info("[SHIFT-SWAP-CONTROLLER] Swap request created successfully with ID: {}", createdRequest.getRequestId());

            return ResponseEntity.ok(createdRequest);

        } catch (Exception e) {

            log.error("[SHIFT-SWAP-CONTROLLER] Error creating swap request. Error: {}", e.getMessage(), e);

            return ResponseEntity.status(500).body(null);

        }

    }
 
    @PutMapping("/approve-by-employee/{id}")

    public ResponseEntity<ShiftSwapRequestDto> approveByEmployee(@PathVariable int id) {

        log.info("[SHIFT-SWAP-CONTROLLER] Approving swap request by employee for request ID: {}", id);

        try {

            ShiftSwapRequestDto approvedRequest = shiftSwapRequestService.approveSwapRequestByEmployee(id);

            log.info("[SHIFT-SWAP-CONTROLLER] Swap request with ID: {} approved by employee successfully", id);

            return ResponseEntity.ok(approvedRequest);

        } catch (Exception e) {

            log.error("[SHIFT-SWAP-CONTROLLER] Error approving swap request by employee for request ID: {}. Error: {}", id, e.getMessage(), e);

            return ResponseEntity.status(500).body(null);

        }

    }
 
    @PutMapping("/approve-by-manager/{id}")

    public ResponseEntity<ShiftSwapRequestDto> approveByManager(@PathVariable int id) {

        log.info("[SHIFT-SWAP-CONTROLLER] Approving swap request by manager for request ID: {}", id);

        try {

            ShiftSwapRequestDto approvedRequest = shiftSwapRequestService.approveSwapRequestByManager(id);

            log.info("[SHIFT-SWAP-CONTROLLER] Swap request with ID: {} approved by manager successfully", id);

            return ResponseEntity.ok(approvedRequest);

        } catch (Exception e) {

            log.error("[SHIFT-SWAP-CONTROLLER] Error approving swap request by manager for request ID: {}. Error: {}", id, e.getMessage(), e);

            return ResponseEntity.status(500).body(null);

        }

    }
 
    @GetMapping("/status/{status}")

    public ResponseEntity<List<ShiftSwapRequestDto>> getRequestsByStatus(@PathVariable String status) {

        log.info("[SHIFT-SWAP-CONTROLLER] Fetching swap requests with status: {}", status);

        try {

            List<ShiftSwapRequestDto> requests = shiftSwapRequestService.getRequestsByStatus(status);

            log.info("[SHIFT-SWAP-CONTROLLER] Successfully fetched {} requests with status: {}", requests.size(), status);

            return ResponseEntity.ok(requests);

        } catch (Exception e) {

            log.error("[SHIFT-SWAP-CONTROLLER] Error fetching requests with status: {}. Error: {}", status, e.getMessage(), e);

            return ResponseEntity.status(500).build();

        }

    }

}

 