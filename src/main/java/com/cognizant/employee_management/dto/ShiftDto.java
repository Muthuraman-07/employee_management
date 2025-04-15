package com.cognizant.employee_management.dto;
 
import java.time.LocalDate;
import java.time.LocalTime;
 
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Data
public class ShiftDto {
    @NotNull(message = "Shift ID cannot be null")
    private int shiftId;
 
    @NotNull(message = "Shift Date cannot be null")
    private LocalDate shiftDate;
 
    @NotNull(message = "Shift Start Time cannot be null")
    private LocalTime shiftStartTime;
 
    @NotNull(message = "Shift End Time cannot be null")
    private LocalTime shiftEndTime;
 
    // Getters and Setters
    public int getShiftId() {
        return shiftId;
    }
 
    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }
 
    public LocalDate getShiftDate() {
        return shiftDate;
    }
 
    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }
 
    public LocalTime getShiftStartTime() {
        return shiftStartTime;
    }
 
    public void setShiftStartTime(LocalTime shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }
 
    public LocalTime getShiftEndTime() {
        return shiftEndTime;
    }
 
    public void setShiftEndTime(LocalTime shiftEndTime) {
        this.shiftEndTime = shiftEndTime;
    }
}