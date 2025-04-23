package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cognizant.employeemanagement.dto.LeaveBalanceDto;
import com.cognizant.employeemanagement.model.Employee;
import com.cognizant.employeemanagement.model.LeaveBalance;
import com.cognizant.employeemanagement.repository.LeaveBalanceRepository;
import com.cognizant.employeemanagement.service.LeaveBalanceServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LeaveBalanceServiceTest {

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LeaveBalanceServiceImpl leaveBalanceService;

    private LeaveBalance leaveBalance;
    private LeaveBalanceDto leaveBalanceDto;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        employee = new Employee();
        employee.setEmployeeId(1);

        leaveBalance = new LeaveBalance();
        leaveBalance.setLeaveBalanceID(100);
        leaveBalance.setEmployee(employee);
        leaveBalance.setLeaveType("Sick Leave");
        leaveBalance.setBalance(10);

        leaveBalanceDto = new LeaveBalanceDto();
        leaveBalanceDto.setLeaveBalanceID(100);
        leaveBalanceDto.setEmployee(employee);
        leaveBalanceDto.setEmployeeId(1);
        leaveBalanceDto.setLeaveType("Sick Leave");
        leaveBalanceDto.setBalance(10);
    }

    @Test
    void testCreateLeaveBalance() {
        // Mock behavior
        when(modelMapper.map(leaveBalanceDto, LeaveBalance.class)).thenReturn(leaveBalance);
        when(leaveBalanceRepository.save(any(LeaveBalance.class))).thenReturn(leaveBalance);
        when(modelMapper.map(leaveBalance, LeaveBalanceDto.class)).thenReturn(leaveBalanceDto);

        // Test
        LeaveBalanceDto result = leaveBalanceService.createLeaveBalance(leaveBalanceDto);

        // Assertions
        assertNotNull(result);
        assertEquals(leaveBalanceDto.getLeaveBalanceID(), result.getLeaveBalanceID());
        assertEquals(leaveBalanceDto.getBalance(), result.getBalance());
        verify(leaveBalanceRepository, times(1)).save(any(LeaveBalance.class));
    }

    @Test
    void testGetAllLeaveBalances() {
        // Mock behavior
        when(leaveBalanceRepository.findAll()).thenReturn(Arrays.asList(leaveBalance));

        // Test
        List<LeaveBalanceDto> result = leaveBalanceService.getAllLeaveBalances();

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(leaveBalance.getLeaveBalanceID(), result.get(0).getLeaveBalanceID());
        verify(leaveBalanceRepository, times(1)).findAll();
    }

    @Test
    void testDeleteLeaveBalance_Success() {
        // Mock behavior
        when(leaveBalanceRepository.existsById(100)).thenReturn(true);
        doNothing().when(leaveBalanceRepository).deleteById(100);

        // Test
        leaveBalanceService.deleteLeaveBalance(100);

        // Verify
        verify(leaveBalanceRepository, times(1)).existsById(100);
        verify(leaveBalanceRepository, times(1)).deleteById(100);
    }

    @Test
    void testDeleteLeaveBalance_NotFound() {
        // Mock behavior
        when(leaveBalanceRepository.existsById(99)).thenReturn(false);

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> leaveBalanceService.deleteLeaveBalance(99));

        // Assertions
        assertTrue(exception.getMessage().contains("LeaveBalance not found"));
        verify(leaveBalanceRepository, times(1)).existsById(99);
    }
}
