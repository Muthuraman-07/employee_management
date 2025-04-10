package com.cognizant.employee_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.employee_management.dto.ReportDTO;
import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.model.Leave;
import com.cognizant.employee_management.repository.AttendanceRepository;
import com.cognizant.employee_management.repository.ShiftRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.repository.LeaveRepository;


@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Override
    @Transactional
    public List<ReportDTO> getAttendanceReport() {
        List<Attendance> attendances = attendanceRepository.findAll();
        return attendances.stream().map(attendance -> {
            ReportDTO dto = new ReportDTO();
            dto.setAttendanceEmployeeId(attendance.getEmployee().getEmployeeId());
            dto.setAttendanceEmployeeName(attendance.getEmployee().getFirstName() + " " + attendance.getEmployee().getLastName());
            dto.setClockInTime(attendance.getClockInTime());
            dto.setClockOutTime(attendance.getClockOutTime());
            dto.setWorkHours(attendance.getWorkHours());
            dto.setIsPresent(attendance.getIsPresent());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ReportDTO> getShiftReport() {
        List<Shift> shifts = shiftRepository.findAll();
        return shifts.stream().map(shift -> {
            ReportDTO dto = new ReportDTO();
            dto.setShiftEmployeeId(shift.getShiftId());  // Assuming shiftId is used for employee identification
            dto.setShiftEmployeeName("N/A");  // No employee name in Shift entity, set to "N/A" or handle differently
            dto.setShiftDate(shift.getShiftDate());
            dto.setShiftStartTime(shift.getShiftStartTime());
            dto.setShiftEndTime(shift.getShiftEndTime());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ReportDTO> getLeaveBalanceReport() {
        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findAll();
        return leaveBalances.stream().map(leaveBalance -> {
            ReportDTO dto = new ReportDTO();
            dto.setLeaveBalanceEmployeeId(leaveBalance.getEmployee().getEmployeeId());
            dto.setLeaveBalanceEmployeeName(leaveBalance.getEmployee().getFirstName() + " " + leaveBalance.getEmployee().getLastName());
            dto.setLeaveType(leaveBalance.getLeaveType());
            dto.setBalance(leaveBalance.getBalance());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ReportDTO> getLeaveReport() {
        List<Leave> leaves = leaveRepository.findAll();
        return leaves.stream().map(leave -> {
            ReportDTO dto = new ReportDTO();
            dto.setLeaveEmployeeId(leave.getEmployee().getEmployeeId());
            dto.setLeaveEmployeeName(leave.getEmployee().getFirstName() + " " + leave.getEmployee().getLastName());
            dto.setLeaveReportType(leave.getLeaveType());
            dto.setLeaveStartDate(leave.getStartDate());  // Ensure this is LocalDate
            dto.setLeaveEndDate(leave.getEndDate());      // Ensure this is LocalDate
            dto.setLeaveStatus(leave.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }
}
