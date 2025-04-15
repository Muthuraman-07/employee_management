package com.cognizant.employee_management.dto;
 
import java.time.LocalDateTime;
 
import com.cognizant.employee_management.model.Employee;
 
import jakarta.validation.constraints.*;
 
import lombok.Data;
 
@Data
public class LeaveDto {
    @NotNull(message = "Leave ID cannot be null")
    @Min(value = 1, message = "Leave ID must be greater than zero")
    private int leaveId;
 
    @NotNull(message = "Employee cannot be null")
    private Employee employee;
 
    @NotNull(message = "Applied Date cannot be null")
    @PastOrPresent(message = "Applied Date must be in the past or present")
    private LocalDateTime appliedDate;
 
    @NotNull(message = "Start Date cannot be null")
    @FutureOrPresent(message = "Start Date must be in the present or future")
    private LocalDateTime startDate;
 
    @NotNull(message = "End Date cannot be null")
    @Future(message = "End Date must be in the future")
    private LocalDateTime endDate;
 
    @NotNull(message = "Status cannot be null")
    @Size(min = 2, max = 20, message = "Status must be between 2 and 20 characters")
    private String status;
 
    @NotNull(message = "Leave Type cannot be null")
    @Size(min = 2, max = 30, message = "Leave Type must be between 2 and 30 characters")
    private String leaveType;
 
    private LocalDateTime approvedDate;
 
}