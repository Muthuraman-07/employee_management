package com.cognizant.employee_management.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ShiftSwapRequestDto {
    private int requestId;          // Unique identifier for the request
    private int requesterId;        // ID of the employee requesting the swap
    private int approverId;         // ID of the employee agreeing to the swap
    private int requesterShiftId;   // Shift ID of the requester
    private int approverShiftId;    // Shift ID of the approver
    private LocalDate requestDate;  // Date of the swap request
    private String status;          // Status of the request (Pending, Submitted to Manager, Approved, Rejected)
}
