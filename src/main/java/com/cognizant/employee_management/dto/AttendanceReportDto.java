package com.cognizant.employee_management.dto;

import java.time.LocalDate;

public class AttendanceReportDto {
    private LocalDate date;
    private long presentCount;

    public AttendanceReportDto(LocalDate date, long presentCount) {
        this.date = date;
        this.presentCount = presentCount;
    }

  
}
