package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.dto.ReportDTO;

public interface ReportsService {
    List<ReportDTO> getAttendanceReport();
    List<ReportDTO> getShiftReport();
    List<ReportDTO> getLeaveBalanceReport();
    List<ReportDTO> getLeaveReport();
}
