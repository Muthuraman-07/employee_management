package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cognizant.employeemanagement.dto.LeaveDto;
import com.cognizant.employeemanagement.model.Employee;
import com.cognizant.employeemanagement.model.Leave;
import com.cognizant.employeemanagement.model.LeaveBalance;
import com.cognizant.employeemanagement.repository.EmployeeRepository;
import com.cognizant.employeemanagement.repository.LeaveBalanceRepository;
import com.cognizant.employeemanagement.repository.LeaveRepository;
import com.cognizant.employeemanagement.service.LeaveServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LeaveServiceTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    private Leave leave;
    private LeaveDto leaveDto;
    private Employee employee;
    private LeaveBalance leaveBalance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        employee = new Employee();
        employee.setEmployeeId(1);

        leave = new Leave();
        leave.setLeaveId(100);
        leave.setEmployee(employee);
        leave.setStartDate(LocalDateTime.now().plusDays(1));
        leave.setEndDate(LocalDateTime.now().plusDays(5));
        leave.setLeaveType("Vacation");
        leave.setStatus("Approved");

        leaveDto = new LeaveDto();
        leaveDto.setStartDate(LocalDateTime.now().plusDays(1));
        leaveDto.setEndDate(LocalDateTime.now().plusDays(5));
        leaveDto.setLeaveType("Vacation");

        leaveBalance = new LeaveBalance();
        leaveBalance.setBalance(10);
    }

    

 
    @Test
    void testDeleteLeave_Success() {
        // Mock behavior
        when(leaveRepository.findById(100)).thenReturn(Optional.of(leave));
        doNothing().when(leaveRepository).delete(leave);

        // Test
        leaveService.deleteLeave(100);

        // Verify
        verify(leaveRepository, times(1)).findById(100);
        verify(leaveRepository, times(1)).delete(leave);
    }

    @Test
    void testDeleteLeave_NotFound() {
        // Mock behavior
        when(leaveRepository.findById(99)).thenReturn(Optional.empty());

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> leaveService.deleteLeave(99));

        // Assertions
        assertTrue(exception.getMessage().contains("Leave not found"));
        verify(leaveRepository, times(1)).findById(99);
    }

    @Test
    void testApplyLeave_Success() {
        // Mock behavior
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
        when(leaveRepository.save(any(Leave.class))).thenReturn(leave);
        when(leaveBalanceRepository.findByEmployeeAndLeaveType(any(Employee.class), eq("Vacation")))
                .thenReturn(leaveBalance);

        // Test
        assertDoesNotThrow(() -> leaveService.applyLeave(1, leaveDto));

        // Verify
        verify(employeeRepository, times(1)).findById(1);
        verify(leaveRepository, times(2)).save(any(Leave.class)); // Called twice: once for initial save, once after approval
        verify(leaveBalanceRepository, times(2)).findByEmployeeAndLeaveType(any(Employee.class), eq("Vacation"));
    }

    

}

