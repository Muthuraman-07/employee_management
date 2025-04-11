package com.cognizant.employee_management.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee_leave")
public class Leave {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveId;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    @NotNull(message = "Employee should not be null")
    private Employee employee;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Applied date should not be null")
    @PastOrPresent(message = "Applied date must be in the past or present")
    private LocalDateTime appliedDate;

    @Column(length = 30)
    @NotNull(message = "Leave type should not be null")
    @Size(min = 2, max = 30, message = "Leave type must be between 2 and 30 characters")
    private String leaveType;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Start date should not be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDateTime startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "End date should not be null")
    @Future(message = "End date must be in the future")
    private LocalDateTime endDate;

    @Column(length = 20)
    @NotNull(message = "Status should not be null")
    @Size(min = 2, max = 20, message = "Status must be between 2 and 20 characters")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime approvedDate;
}
