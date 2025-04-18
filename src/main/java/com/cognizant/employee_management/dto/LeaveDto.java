package com.cognizant.employee_management.dto;
 
import java.time.LocalDateTime;
 
import com.cognizant.employee_management.model.Employee;
 
import jakarta.validation.constraints.*;
 
import lombok.Data;
 
@Data
public class LeaveDto {
 
    @NotNull(message = "Start Date cannot be null")
    @FutureOrPresent(message = "Start Date must be in the present or future")
    private LocalDateTime startDate;
 
    @NotNull(message = "End Date cannot be null")
    @Future(message = "End Date must be in the future")
    private LocalDateTime endDate;
 
    @NotNull(message = "Leave Type cannot be null")
    @Size(min = 2, max = 30, message = "Leave Type must be between 2 and 30 characters")
    private String leaveType;
    
    private String status;
}