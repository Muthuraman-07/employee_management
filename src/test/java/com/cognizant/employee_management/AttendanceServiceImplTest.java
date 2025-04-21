package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.repository.AttendanceRepository;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.service.AttendanceServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceImplTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    private Attendance attendance;
    private AttendanceDto attendanceDto;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize Employee
        employee = new Employee();
        employee.setEmployeeId(1);
        employee.setShift(null); // Add shift data if required for testing logic

        // Initialize Attendance
        attendance = new Attendance();
        attendance.setAttendanceID(101);
        attendance.setEmployee(employee);
        attendance.setClockInTime(LocalDateTime.of(2023, 4, 1, 9, 0));
        attendance.setClockOutTime(LocalDateTime.of(2023, 4, 1, 18, 0));
        attendance.setWorkHours(9);
        attendance.setIsPresent(1);

        // Initialize AttendanceDto
        attendanceDto = new AttendanceDto();
        attendanceDto.setClockInTime(LocalDateTime.of(2023, 4, 1, 9, 0));
        attendanceDto.setClockOutTime(LocalDateTime.of(2023, 4, 1, 18, 0));
    }

    @Test
    void testGetAllAttendance() {
        // Mock behavior
        when(attendanceRepository.findAll()).thenReturn(Arrays.asList(attendance));

        // Test
        assertNotNull(attendanceService.getAllAttendance());
        verify(attendanceRepository, times(1)).findAll();
    }

    

    @Test
    void testSaveAttendance_EmployeeNotFound() {
        // Mock behavior for employee not found
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> attendanceService.saveAttendance(1, attendanceDto));

        // Assertions
        assertTrue(exception.getMessage().contains("Employee not found"));
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteAttendance_Success() {
        // Mock behavior
        when(attendanceRepository.existsById(101)).thenReturn(true);
        doNothing().when(attendanceRepository).deleteById(101);

        // Test
        assertDoesNotThrow(() -> attendanceService.deleteAttendance(101));

        // Verify
        verify(attendanceRepository, times(1)).existsById(101);
        verify(attendanceRepository, times(1)).deleteById(101);
    }

    @Test
    void testDeleteAttendance_NotFound() {
        // Mock behavior for record not found
        when(attendanceRepository.existsById(99)).thenReturn(false);

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> attendanceService.deleteAttendance(99));

        // Assertions
        assertTrue(exception.getMessage().contains("Attendance not found"));
        verify(attendanceRepository, times(1)).existsById(99);
    }
}
