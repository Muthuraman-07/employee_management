package com.cognizant.employee_management.dto;
 
import java.time.LocalDateTime;
 
import com.cognizant.employee_management.model.Employee;
 
import jakarta.validation.constraints.*;
import lombok.Data;
 
@Data
public class AttendanceDto {
    @NotNull(message = "Attendance ID cannot be null")
    @Min(value = 1, message = "Attendance ID must be greater than zero")
    private int attendanceID;
 
    @NotNull(message = "Employee cannot be null")
    private Employee employee;
 
    @NotNull(message = "Clock In Time cannot be null")
    @PastOrPresent(message = "Clock In Time must be in the past or present")
    private LocalDateTime clockInTime;
 
    @NotNull(message = "Clock Out Time cannot be null")
    @PastOrPresent(message = "Clock Out Time must be in the past or present")
    private LocalDateTime clockOutTime;
 
    @NotNull(message = "Work Hours cannot be null")
    @Positive(message = "Work Hours must be positive")
    private float workHours;
 
    @NotNull(message = "Is Present cannot be null")
    @Min(value = 1, message = "Is Present must be greater than zero")
    private int isPresent;
 
    // Getters and Setters
    public int getAttendanceID() {
        return attendanceID;
    }
 
    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }
 
    public Employee getEmployee() {
        return employee;
    }
 
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
 
    public LocalDateTime getClockInTime() {
        return clockInTime;
    }
 
    public void setClockInTime(LocalDateTime clockInTime) {
        this.clockInTime = clockInTime;
    }
 
    public LocalDateTime getClockOutTime() {
        return clockOutTime;
    }
 
    public void setClockOutTime(LocalDateTime clockOutTime) {
        this.clockOutTime = clockOutTime;
    }
 
    public float getWorkHours() {
        return workHours;
    }
 
    public void setWorkHours(float workHours) {
        this.workHours = workHours;
    }
 
    public int getIsPresent() {
        return isPresent;
    }
 
    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }
}