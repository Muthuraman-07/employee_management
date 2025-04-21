package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.dto.returnLeaveDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.Leave;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.repository.LeaveRepository;
import com.cognizant.employee_management.service.LeaveServiceImpl;

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
    void testGetAllPendingLeaveRequests() {
        // Mock behavior
        when(leaveRepository.findByStatus("Pending")).thenReturn(Arrays.asList(leave));
        when(modelMapper.map(any(Leave.class), eq(returnLeaveDto.class))).thenReturn(new returnLeaveDto());

        // Test
        List<returnLeaveDto> result = leaveService.getAllPendingLeaveRequests("Pending");

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(leaveRepository, times(1)).findByStatus("Pending");
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

