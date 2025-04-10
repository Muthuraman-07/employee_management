package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.ReportDTO;
import com.cognizant.employee_management.service.ReportsService;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportService;

    @GetMapping("/attendance")
    public List<ReportDTO> getAttendanceReport() {
        return reportService.getAttendanceReport();
    }

    @GetMapping("/shifts")
    public List<ReportDTO> getShiftReport() {
       return reportService.getShiftReport();
    }

    @GetMapping("/leave-balance")
    public List<ReportDTO> getLeaveBalanceReport() {
        return reportService.getLeaveBalanceReport();
    }

    @GetMapping("/leave")
    public List<ReportDTO> getLeaveReport() {
        return reportService.getLeaveReport();
    }
}
