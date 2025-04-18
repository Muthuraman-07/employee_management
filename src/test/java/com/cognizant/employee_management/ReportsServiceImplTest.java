package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.employee_management.dto.ReportDTO;
import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.Leave;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.repository.AttendanceRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.repository.LeaveRepository;
import com.cognizant.employee_management.repository.ShiftRepository;
import com.cognizant.employee_management.service.ReportsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReportsServiceImplTest<ReportDTO> {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private ShiftRepository shiftRepository;

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

    @Mock
    private LeaveRepository leaveRepository;

    @InjectMocks
    private ReportsServiceImpl reportsService;

    private Attendance attendance;
    private Shift shift;
    private LeaveBalance leaveBalance;
    private Leave leave;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setClockInTime(LocalDateTime.of(2025, 4, 16, 9, 0));
        attendance.setClockOutTime(LocalDateTime.of(2025, 4, 16, 17, 0));
        attendance.setWorkHours(8);
        attendance.setIsPresent(1);

        shift = new Shift();
        shift.setShiftId(1);
        shift.setShiftDate(LocalDate.of(2025, 4, 16));
        shift.setShiftStartTime(LocalTime.of(9, 0));
        shift.setShiftEndTime(LocalTime.of(17, 0));

        leaveBalance = new LeaveBalance();
        leaveBalance.setEmployee(employee);
        leaveBalance.setLeaveType("Sick Leave");
        leaveBalance.setBalance(10);

        leave = new Leave();
        leave.setEmployee(employee);
        leave.setLeaveType("Sick Leave");
        leave.setStartDate(LocalDateTime.of(2025, 4, 16, 9, 0));
        leave.setEndDate(LocalDateTime.of(2025, 4, 17, 17, 0));
        leave.setStatus("Approved");
    }

    @Test
    void testGetAttendanceReport() {
        when(attendanceRepository.findAll()).thenReturn(Arrays.asList(attendance));

        List<ReportDTO> result = reportsService.getAttendanceReport();

        assertEquals(1, result.size());
        ReportDTO dto = result.get(0);
        assertEquals(1, dto.getAttendanceEmployeeId());
        assertEquals("John Doe", dto.getAttendanceEmployeeName());
        assertEquals(LocalDateTime.of(2025, 4, 16, 9, 0), dto.getClockInTime());
        assertEquals(LocalDateTime.of(2025, 4, 16, 17, 0), dto.getClockOutTime());
        assertEquals(8, dto.getWorkHours());
        assertEquals(1, dto.getIsPresent());

        verify(attendanceRepository, times(1)).findAll();
    }

    @Test
    void testGetShiftReport() {
        when(shiftRepository.findAll()).thenReturn(Arrays.asList(shift));

        List<ReportDTO> result = reportsService.getShiftReport();

        assertEquals(1, result.size());
        ReportDTO dto = result.get(0);
        assertEquals(1, dto.getShiftEmployeeId());
        assertEquals("N/A", dto.getShiftEmployeeName());
        assertEquals(LocalDate.of(2025, 4, 16), dto.getShiftDate());
        assertEquals(LocalTime.of(9, 0), dto.getShiftStartTime());
        assertEquals(LocalTime.of(17, 0), dto.getShiftEndTime());

        verify(shiftRepository, times(1)).findAll();
    }

    @Test
    void testGetLeaveBalanceReport() {
        when(leaveBalanceRepository.findAll()).thenReturn(Arrays.asList(leaveBalance));

        List<ReportDTO> result = reportsService.getLeaveBalanceReport();

        assertEquals(1, result.size());
        ReportDTO dto = result.get(0);
        assertEquals(1, dto.getLeaveBalanceEmployeeId());
        assertEquals("John Doe", dto.getLeaveBalanceEmployeeName());
        assertEquals("Sick Leave", dto.getLeaveType());
        assertEquals(10, dto.getBalance());

        verify(leaveBalanceRepository, times(1)).findAll();
    }

    @Test
    void testGetLeaveReport() {
        when(leaveRepository.findAll()).thenReturn(Arrays.asList(leave));

        List<ReportDTO> result = reportsService.getLeaveReport();

        assertEquals(1, result.size());
        ReportDTO dto = result.get(0);
        assertEquals(1, dto.getLeaveEmployeeId());
        assertEquals("John Doe", dto.getLeaveEmployeeName());
        assertEquals("Sick Leave", dto.getLeaveReportType());
        assertEquals(LocalDateTime.of(2025, 4, 16, 9, 0), dto.getLeaveStartDate());
        assertEquals(LocalDateTime.of(2025, 4, 17, 17, 0), dto.getLeaveEndDate());
        assertEquals("Approved", dto.getLeaveStatus());

        verify(leaveRepository, times(1)).findAll();
    }
}

