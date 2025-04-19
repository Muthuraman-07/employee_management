package com.cognizant.employee_management.dto;
 
import com.cognizant.employee_management.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.*;
import lombok.Data;
 
@Data
public class LeaveBalanceDto {
    @NotNull(message = "Leave Balance ID cannot be null")
    @Min(value = 1, message = "Leave Balance ID must be greater than zero")
    private int leaveBalanceID;
 
    @NotNull(message = "Employee cannot be null")
    @JsonIgnore
    private Employee employee;
 
    @NotNull(message = "Leave Type cannot be null")
    @Size(min = 2, max = 20, message = "Leave Type must be between 2 and 20 characters")
    private String leaveType;
 
    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance must be zero or positive")
    private int balance;
}