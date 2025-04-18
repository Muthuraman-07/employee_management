package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cognizant.employee_management.dto.LeaveDto;
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
    private EmployeeRepository employeeRepository;

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

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
        leave = new Leave();
        leave.setLeaveId(1);
        leave.setStatus("Pending");
        leave.setStartDate(LocalDateTime.now());
        leave.setEndDate(LocalDateTime.now().plusDays(5));

        leaveDto = new LeaveDto();
        leaveDto.setLeaveType("Sick Leave");

        employee = new Employee();
        employee.setEmployeeId(1);

        leaveBalance = new LeaveBalance();
        leaveBalance.setBalance(10);
    }

    @Test
    void testGetAllLeaves() {
        when(leaveRepository.findAll()).thenReturn(Arrays.asList(leave));
        when(modelMapper.map(any(Leave.class), eq(LeaveDto.class))).thenReturn(new LeaveDto());

        assertNotNull(leaveService.getAllLeaves());
        verify(leaveRepository, times(1)).findAll();
    }

    @Test
    void testGetLeaveById() {
        when(leaveRepository.findById(1)).thenReturn(Optional.of(leave));
        when(modelMapper.map(any(Leave.class), eq(LeaveDto.class))).thenReturn(leaveDto);

        LeaveDto result = leaveService.getLeaveById(1);
        assertNotNull(result);
        verify(leaveRepository, times(1)).findById(1);
    }

    @Test
    void testGetLeaveById_NotFound() {
        when(leaveRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                leaveService.getLeaveById(99)
        );

        assertTrue(exception.getMessage().contains("Leave not found with id: 99"));
    }

    @Test
    void testCreateLeave() {
        when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
        when(leaveRepository.save(any(Leave.class))).thenReturn(leave);
        when(modelMapper.map(any(Leave.class), eq(LeaveDto.class))).thenReturn(leaveDto);

        LeaveDto result = leaveService.createLeave(leaveDto);
        assertNotNull(result);
        verify(leaveRepository, times(1)).save(any(Leave.class));
    }

    @Test
    void testUpdateLeave() {
        when(leaveRepository.findById(1)).thenReturn(Optional.of(leave));
        when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
        when(leaveRepository.save(any(Leave.class))).thenReturn(leave);
        when(modelMapper.map(any(Leave.class), eq(LeaveDto.class))).thenReturn(leaveDto);

        LeaveDto result = leaveService.updateLeave(1, leaveDto);
        assertNotNull(result);
        verify(leaveRepository, times(1)).findById(1);
        verify(leaveRepository, times(1)).save(any(Leave.class));
    }

    @Test
    void testDeleteLeave() {
        when(leaveRepository.findById(1)).thenReturn(Optional.of(leave));

        leaveService.deleteLeave(1);
        verify(leaveRepository, times(1)).findById(1);
        verify(leaveRepository, times(1)).delete(any(Leave.class));
    }

//    @Test
//    void testApplyLeave() {
//        // Mocking the employee repository to return the employee
//        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
//        
//        // Mocking the model mapper to map LeaveDto to Leave
//        when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
//       
//        // Mocking the leave repository to save the leave and handle the "Approved" status scenario
//        when(leaveRepository.save(any(Leave.class))).thenAnswer(invocation -> {
//            Leave savedLeave = invocation.getArgument(0);
//            if ("Approved".equals(savedLeave.getStatus())) {
//                savedLeave.setApprovedDate(LocalDateTime.now()); // Simulate approval date being set
//            }
//            return savedLeave;
//        });
//        
//        // Mocking the leave balance repository to return leave balance
//        when(leaveBalanceRepository.findByEmployeeAndLeaveType(any(Employee.class), anyString())).thenReturn(leaveBalance);
//
//        // Applying the leave
//        leaveService.applyLeave(1, leaveDto);
//        // Verifying interactions and assertions
//        verify(employeeRepository, times(1)).findById(1);
//        verify(leaveRepository, times(1)).save(any(Leave.class)); // Save is called once
//        verify(leaveBalanceRepository, times(1)).findByEmployeeAndLeaveType(any(Employee.class), anyString());
//        assertTrue(leave.getStatus().equals("Approved") || leave.getStatus().equals("Pending"));
//    }
    @Test
    void testApplyLeave() {
        // Mocking the employee repository to return the employee
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        
        // Mocking the model mapper to map LeaveDto to Leave
        when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
        
        // Mocking the leave repository to save the leave and handle the "Approved" status scenario
        when(leaveRepository.save(any(Leave.class))).thenAnswer(invocation -> {
            Leave savedLeave = invocation.getArgument(0);
            if ("Approved".equals(savedLeave.getStatus())) {
                savedLeave.setApprovedDate(LocalDateTime.now()); // Simulate approval date being set
            }
            return savedLeave;
        });
        
        // Mocking the leave balance repository to return leave balance
        when(leaveBalanceRepository.findByEmployeeAndLeaveType(any(Employee.class), anyString())).thenReturn(leaveBalance);

        // Applying the leave
        leaveDto.setStatus("Approved"); // Set the status to Approved for testing
        leaveService.applyLeave(1, leaveDto);

        // Verifying interactions and assertions
        verify(employeeRepository, times(1)).findById(1);
        verify(leaveRepository, times(2)).save(any(Leave.class)); // Save is called twice: once initially and once after approval
        verify(leaveBalanceRepository, times(1)).findByEmployeeAndLeaveType(any(Employee.class), anyString());
        assertTrue(leave.getStatus().equals("Approved"));
    }

}

//package com.cognizant.employee_management;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import com.cognizant.employee_management.dto.LeaveDto;
//import com.cognizant.employee_management.model.Employee;
//import com.cognizant.employee_management.model.Leave;
//import com.cognizant.employee_management.model.LeaveBalance;
//import com.cognizant.employee_management.repository.EmployeeRepository;
//import com.cognizant.employee_management.repository.LeaveBalanceRepository;
//import com.cognizant.employee_management.repository.LeaveRepository;
//import com.cognizant.employee_management.service.LeaveServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class LeaveServiceTest {
//
//    @Mock
//    private LeaveRepository leaveRepository;
//
//    @Mock
//    private EmployeeRepository employeeRepository;
//
//    @Mock
//    private LeaveBalanceRepository leaveBalanceRepository;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private LeaveServiceImpl leaveService;
//
//    private Leave leave;
//    private LeaveDto leaveDto;
//    private Employee employee;
//    private LeaveBalance leaveBalance;
//
//    @BeforeEach
//    void setUp() {
//        leave = new Leave();
//        leave.setLeaveId(1);
//        leave.setStatus("Pending");
//        leave.setStartDate(LocalDateTime.now());
//        leave.setEndDate(LocalDateTime.now().plusDays(5));
//
//        leaveDto = new LeaveDto();
//        leaveDto.setLeaveType();
//        leaveDto.setStatus("Pending");
//        
//        employee = new Employee();
//        employee.setEmployeeId(1);
//        leaveBalance = new LeaveBalance();
//        leaveBalance.setBalance(10);
//    }
//
//    @Test
//    void testGetAllLeaves() {
//        when(leaveRepository.findAll()).thenReturn(Arrays.asList(leave));
//        when(modelMapper.map(any(Leave.class), eq(returnleavedto.class))).thenReturn(new returnleavedto());
//
//        assertNotNull(leaveService.getAllLeaves());
//        verify(leaveRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetLeaveById() {
//        when(leaveRepository.findById(1)).thenReturn(Optional.of(leave));
//        when(modelMapper.map(any(Leave.class), eq(LeaveDto.class))).thenReturn(leaveDto);
//
//        LeaveDto result = leaveService.getLeaveById(1);
//        assertNotNull(result);
//        verify(leaveRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void testGetLeaveById_NotFound() {
//        when(leaveRepository.findById(99)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(RuntimeException.class, () ->
//                leaveService.getLeaveById(99)
//        );
//
//        assertTrue(exception.getMessage().contains("Leave not found with id: 99"));
//    }
//
//    @Test
//    void testCreateLeave() {
//        when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
//        when(leaveRepository.save(any(Leave.class))).thenReturn(leave);
//        when(modelMapper.map(any(Leave.class), eq(LeaveDto.class))).thenReturn(leaveDto);
//
//        LeaveDto result = leaveService.createLeave(leaveDto);
//        assertNotNull(result);
//        verify(leaveRepository, times(1)).save(any(Leave.class));
//    }
//
//    @Test
//    void testUpdateLeave() {
//        when(leaveRepository.findById(1)).thenReturn(Optional.of(leave));
//        when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
//        when(leaveRepository.save(any(Leave.class))).thenReturn(leave);
//        when(modelMapper.map(any(Leave.class), eq(LeaveDto.class))).thenReturn(leaveDto);
//
//        LeaveDto result = leaveService.updateLeave(1, leaveDto);
//        assertNotNull(result);
//        verify(leaveRepository, times(1)).findById(1);
//        verify(leaveRepository, times(1)).save(any(Leave.class));
//    }
//
//    @Test
//    void testDeleteLeave() {
//        when(leaveRepository.findById(1)).thenReturn(Optional.of(leave));
//
//        leaveService.deleteLeave(1);
//        verify(leaveRepository, times(1)).findById(1);
//        verify(leaveRepository, times(1)).delete(any(Leave.class));
//    }
//
////    @Test
////    void testApplyLeave() {
////        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
////        when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
////        when(leaveRepository.save(any(Leave.class))).thenReturn(leave);
////        when(leaveBalanceRepository.findByEmployeeAndLeaveType(any(Employee.class), anyString())).thenReturn(leaveBalance);
////
////        leaveService.applyLeave(1, leaveDto);
////        verify(employeeRepository, times(1)).findById(1);
////        verify(leaveRepository, times(1)).save(any(Leave.class));
////    }
////}
//@Test
//void testApplyLeave() {
//    // Mocking the employee repository to return the employee
//    when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
//    
//    // Mocking the model mapper to map LeaveDto to Leave
//    when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
//    
//    // Mocking the leave repository to save the leave
//    when(leaveRepository.save(any(Leave.class))).thenReturn(leave);
//    
//    // Mocking the leave balance repository to return the leave balance
//    when(leaveBalanceRepository.findByEmployeeAndLeaveType(any(Employee.class), anyString())).thenReturn(leaveBalance);
//
//    // Applying the leave
//    leaveService.applyLeave(1, leaveDto);
//
//    // Verifying the interactions
//    verify(employeeRepository, times(1)).findById(1);
//    verify(leaveRepository, times(2)).save(any(Leave.class)); // save is calleda twice: once initially and once if approved
//    verify(leaveBalanceRepository, times(1)).findByEmployeeAndLeaveType(any(Employee.class), anyString());
//}
////    @Test
////    void testApplyLeave() {
////        // Mocking the employee repository to return the employee
////        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
////        
////        // Mocking the model mapper to map LeaveDto to Leave
////        when(modelMapper.map(any(LeaveDto.class), eq(Leave.class))).thenReturn(leave);
////        
////        // Mocking the leave repository to save the leave
////        when(leaveRepository.save(any(Leave.class))).thenAnswer(invocation -> {
////            Leave savedLeave = invocation.getArgument(0);
////            savedLeave.setStatus("Approved"); // Set the status to "Approved" to trigger the second save
////            return savedLeave;
////        });
////        
////        // Mocking the leave balance repository to return the leave balance
////        when(leaveBalanceRepository.findByEmployeeAndLeaveType(any(Employee.class), anyString())).thenReturn(leaveBalance);
////
////        // Applying the leave
////        leaveService.applyLeave(1, leaveDto);
////
////        // Verifying the interactions
////        verify(employeeRepository, times(1)).findById(1);
////        verify(leaveRepository, times(2)).save(any(Leave.class)); // save is called twice: once initially and once if approved
////        verify(leaveBalanceRepository, times(1)).findByEmployeeAndLeaveType(any(Employee.class), anyString());
////    }
//
//}