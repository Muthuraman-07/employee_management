package com.cognizant.employee_management.dto;
 
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
 
@Data
public class AttendanceDto {
 
    @NotNull(message = "Clock In Time cannot be null")
    @PastOrPresent(message = "Clock In Time must be in the past or present")
    private LocalDateTime clockInTime;
 
    @NotNull(message = "Clock Out Time cannot be null")
    @PastOrPresent(message = "Clock Out Time must be in the past or present")
    private LocalDateTime clockOutTime;
 
 
}