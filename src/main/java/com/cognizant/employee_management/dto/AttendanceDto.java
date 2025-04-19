package com.cognizant.employee_management.dto;
 
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Data
public class AttendanceDto {
    @NotNull(message = "Attendance ID cannot be null")
    @Min(value = 1, message = "Attendance ID must be greater than zero")
    private int attendanceID;
 
    @NotNull(message = "Employee Id cannot be null")
    private int employeeId;
 
    @NotNull(message = "Clock In Time cannot be null")
//    @PastOrPresent(message = "Clock In Time must be in the past or present")
    private LocalDateTime clockInTime;
 
    @NotNull(message = "Clock Out Time cannot be null")
//    @PastOrPresent(message = "Clock Out Time must be in the past or present")
    private LocalDateTime clockOutTime;
 
//    @NotNull(message = "Work Hours cannot be null")
//    @Positive(message = "Work Hours must be positive")
    private float workHours;
 
//    @NotNull(message = "Is Present cannot be null")
//    @Min(value = 1, message = "Is Present must be greater than zero")
    private int isPresent;
 
    
}