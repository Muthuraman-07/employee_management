package com.cognizant.employee_management.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReportDTO {
    // Attendance Report Fields
    @NotNull(message = "Attendance Employee ID cannot be null")
    private int attendanceEmployeeId;

    @NotNull(message = "Attendance Employee Name cannot be null")
    @Size(min = 1, max = 100, message = "Attendance Employee Name must be between 1 and 100 characters")
    private String attendanceEmployeeName;

    @NotNull(message = "Clock In Time cannot be null")
    private LocalDateTime clockInTime;

    @NotNull(message = "Clock Out Time cannot be null")
    private LocalDateTime clockOutTime;

    @NotNull(message = "Work Hours cannot be null")
    private float workHours;

    @NotNull(message = "Is Present cannot be null")
    private int isPresent;

    // Shift Report Fields
    @NotNull(message = "Shift Employee ID cannot be null")
    private int shiftEmployeeId;

    @NotNull(message = "Shift Employee Name cannot be null")
    @Size(min = 1, max = 100, message = "Shift Employee Name must be between 1 and 100 characters")
    private String shiftEmployeeName;

    @NotNull(message = "Shift Date cannot be null")
    private LocalDate shiftDate;

    @NotNull(message = "Shift Start Time cannot be null")
    private LocalTime shiftStartTime;

    @NotNull(message = "Shift End Time cannot be null")
    private LocalTime shiftEndTime;

    // Leave Balance Report Fields
    @NotNull(message = "Leave Balance Employee ID cannot be null")
    private int leaveBalanceEmployeeId;

    @NotNull(message = "Leave Balance Employee Name cannot be null")
    @Size(min = 1, max = 100, message = "Leave Balance Employee Name must be between 1 and 100 characters")
    private String leaveBalanceEmployeeName;

    @NotNull(message = "Leave Type cannot be null")
    @Size(min = 1, max = 50, message = "Leave Type must be between 1 and 50 characters")
    private String leaveType;

    @NotNull(message = "Balance cannot be null")
    private int balance;

    // Leave Report Fields
    @NotNull(message = "Leave Employee ID cannot be null")
    private int leaveEmployeeId;

    @NotNull(message = "Leave Employee Name cannot be null")
    @Size(min = 1, max = 100, message = "Leave Employee Name must be between 1 and 100 characters")
    private String leaveEmployeeName;

    @NotNull(message = "Leave Report Type cannot be null")
    @Size(min = 1, max = 50, message = "Leave Report Type must be between 1 and 50 characters")
    private String leaveReportType;

    @NotNull(message = "Leave Start Date cannot be null")
    private LocalDateTime leaveStartDate;

    @NotNull(message = "Leave End Date cannot be null")
    private LocalDateTime leaveEndDate;

    @NotNull(message = "Leave Status cannot be null")
    @Size(min = 1, max = 50, message = "Leave Status must be between 1 and 50 characters")
    private String leaveStatus;
}

