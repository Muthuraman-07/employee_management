package com.cognizant.employee_management.dto;

public class LeaveUsageReportDto {
    private String leaveType;
    private long usageCount;

    public LeaveUsageReportDto(String leaveType, long usageCount) {
        this.leaveType = leaveType;
        this.usageCount = usageCount;
    }

    // Getters and setters
}
