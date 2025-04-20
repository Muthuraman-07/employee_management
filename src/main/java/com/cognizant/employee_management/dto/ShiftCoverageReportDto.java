package com.cognizant.employee_management.dto;

public class ShiftCoverageReportDto {
    private String shiftTime;
    private long employeeCount;

    public ShiftCoverageReportDto(String shiftTime, long employeeCount) {
        this.shiftTime = shiftTime;
        this.employeeCount = employeeCount;
    }

    // Getters and setters
}
