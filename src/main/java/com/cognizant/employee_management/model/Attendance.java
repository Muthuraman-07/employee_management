package com.cognizant.employee_management.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attendanceID;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    @NotNull(message = "Employee should not be null")
    private Employee employee;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Clock-in time should not be null")
    @PastOrPresent(message = "Clock-in time must be in the past or present")
    private LocalDateTime clockInTime;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Clock-out time should not be null")
    @PastOrPresent(message = "Clock-out time must be in the past or present")
    private LocalDateTime clockOutTime;

    @NotNull(message = "Work hours should not be null")
    @Positive(message = "Work hours must be positive")
    private float workHours;

    @NotNull(message = "Is Present cannot be null")
    @Min(value = 1, message = "Is Present must be greater than zero")
    private int isPresent;

    // Getters and setters
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
