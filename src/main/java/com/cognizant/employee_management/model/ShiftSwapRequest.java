package com.cognizant.employee_management.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
public class ShiftSwapRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    @Column(nullable = false)
    private int requesterId; // Employee A (requester)

    @Column(nullable = false)
    private int approverId; // Employee B (approver)

    @Column(nullable = false)
    private int requesterShiftId; // Shift ID of Employee A

    @Column(nullable = false)
    private int approverShiftId; // Shift ID of Employee B

    @Column(nullable = false)
    private LocalDate requestDate; // Date the swap was requested

    @Column(nullable = false)
    private String status; // Pending, Approved, Rejected
}
