package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cognizant.employee_management.dto.LeaveBalanceDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.service.LeaveBalanceServiceImpl;

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

    @BeforeEach
    void setUp() {
        leaveBalance = new LeaveBalance();
        leaveBalance.setLeaveBalanceID(1);
        leaveBalance.setEmployee(new Employee());
        leaveBalance.setLeaveType("Sick Leave");
        leaveBalance.setBalance(10);

        leaveBalanceDto = new LeaveBalanceDto();
        leaveBalanceDto.setLeaveBalanceID(1);
        leaveBalanceDto.setEmployee(new Employee());
        leaveBalanceDto.setLeaveType("Sick Leave");
        leaveBalanceDto.setBalance(10);
    }

    @Test
    void testCreateLeaveBalance() {
        when(modelMapper.map(leaveBalanceDto, LeaveBalance.class)).thenReturn(leaveBalance);
        when(leaveBalanceRepository.save(leaveBalance)).thenReturn(leaveBalance);
        when(modelMapper.map(leaveBalance, LeaveBalanceDto.class)).thenReturn(leaveBalanceDto);

        LeaveBalanceDto result = leaveBalanceService.createLeaveBalance(leaveBalanceDto);

        assertEquals(leaveBalanceDto, result);
        verify(leaveBalanceRepository, times(1)).save(leaveBalance);
    }

    @Test
    void testGetLeaveBalanceById() {
        when(leaveBalanceRepository.findById(1)).thenReturn(Optional.of(leaveBalance));
        when(modelMapper.map(leaveBalance, LeaveBalanceDto.class)).thenReturn(leaveBalanceDto);

        LeaveBalanceDto result = leaveBalanceService.getLeaveBalanceById(1);

        assertEquals(leaveBalanceDto, result);
        verify(leaveBalanceRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllLeaveBalances() {
        when(leaveBalanceRepository.findAll()).thenReturn(Arrays.asList(leaveBalance));
        when(modelMapper.map(leaveBalance, LeaveBalanceDto.class)).thenReturn(leaveBalanceDto);

        List<LeaveBalanceDto> result = leaveBalanceService.getAllLeaveBalances();

        assertEquals(1, result.size());
        assertEquals(leaveBalanceDto, result.get(0));
        verify(leaveBalanceRepository, times(1)).findAll();
    }

    @Test
    void testUpdateLeaveBalance() {
        when(leaveBalanceRepository.findById(1)).thenReturn(Optional.of(leaveBalance));
        when(leaveBalanceRepository.save(leaveBalance)).thenReturn(leaveBalance);
        when(modelMapper.map(leaveBalance, LeaveBalanceDto.class)).thenReturn(leaveBalanceDto);

        LeaveBalanceDto result = leaveBalanceService.updateLeaveBalance(1, leaveBalanceDto);

        assertEquals(leaveBalanceDto, result);
        verify(leaveBalanceRepository, times(1)).findById(1);
        verify(leaveBalanceRepository, times(1)).save(leaveBalance);
    }

//    @Test
//    void testDeleteLeaveBalance() {
//        when(leaveBalanceRepository.existsById(1)).thenReturn(true);
//
//        leaveBalanceService.deleteLeaveBalance(1);
//
//        verify(leaveBalanceRepository, times(1)).existsById(1);
//        verify(leaveBalanceRepository, times(1)).deleteById(1);
//    }
    @Test
    void testDeleteLeaveBalance() {
        // Mock the repository to return true for existsById
        when(leaveBalanceRepository.existsById(1)).thenReturn(true);

        // Call the service method
        leaveBalanceService.deleteLeaveBalance(1);

        // Verify the interactions
        verify(leaveBalanceRepository, times(1)).existsById(1);
        verify(leaveBalanceRepository, times(1)).deleteById(1);
    }

}
