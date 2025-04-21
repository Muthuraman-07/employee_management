package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.dto.returnEmployeeDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.repository.AttendanceRepository;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.repository.LeaveRepository;
import com.cognizant.employee_management.repository.ShiftRepository;
import com.cognizant.employee_management.service.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private ShiftRepository shiftRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDto employeeDto;
    private Shift shift;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize Employee and EmployeeDto
        employee = new Employee();
        employee.setEmployeeId(1);
        employee.setManagerId(10);
        employee.setUsername("johndoe");
        employee.setPassword("SecureP@ss123");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPhoneNumber("9876543210");
        employee.setDepartment("HR");
        employee.setRole("ROLE_EMPLOYEE");
        employee.setJoinedDate(LocalDate.of(2022, 1, 1));

        shift = new Shift();
        shift.setShiftId(101);

        employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(1);
        employeeDto.setManagerId(10);
        employeeDto.setUsername("johndoe");
        employeeDto.setPassword("SecureP@ss123");
        employeeDto.setFirstName("John");
        employeeDto.setLastName("Doe");
        employeeDto.setEmail("john.doe@example.com");
        employeeDto.setPhoneNumber("9876543210");
        employeeDto.setDepartment("HR");
        employeeDto.setRole("ROLE_EMPLOYEE");
        employeeDto.setShiftId(101);
        employeeDto.setJoinedDate(LocalDate.of(2022, 1, 1));
    }

    @Test
    void testGetAllEmployees() {
        // Mock behavior
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        when(modelMapper.map(employee, returnEmployeeDto.class)).thenReturn(new returnEmployeeDto());

        // Test
        List<returnEmployeeDto> result = employeeService.getAllEmployees();

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testUpdateEmployee_Success() {
        // Mock behavior
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(shiftRepository.findById(101)).thenReturn(Optional.of(shift));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        // Test
        EmployeeDto result = employeeService.updateEmployee(1, employeeDto);

        // Assertions
        assertNotNull(result);
        assertEquals(employeeDto.getEmployeeId(), result.getEmployeeId());
        verify(employeeRepository, times(1)).findById(1);
        verify(shiftRepository, times(1)).findById(101);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_EmployeeNotFound() {
        // Mock behavior
        when(employeeRepository.findById(99)).thenReturn(Optional.empty());

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> employeeService.updateEmployee(99, employeeDto));

        // Assertions
        assertTrue(exception.getMessage().contains("Employee not found"));
        verify(employeeRepository, times(1)).findById(99);
    }

    @Test
    void testDeleteEmployeeById_Success() {
        // Mock behavior
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        doNothing().when(attendanceRepository).deleteByEmployee_EmployeeId(1);
        doNothing().when(leaveRepository).deleteByEmployee_EmployeeId(1);
        doNothing().when(leaveBalanceRepository).deleteByEmployee_EmployeeId(1);
        doNothing().when(employeeRepository).delete(employee);

        // Test
        assertDoesNotThrow(() -> employeeService.deleteEmployeeById(1));

        // Verify
        verify(employeeRepository, times(1)).findById(1);
        verify(attendanceRepository, times(1)).deleteByEmployee_EmployeeId(1);
        verify(leaveRepository, times(1)).deleteByEmployee_EmployeeId(1);
        verify(leaveBalanceRepository, times(1)).deleteByEmployee_EmployeeId(1);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void testDeleteEmployeeById_EmployeeNotFound() {
        // Mock behavior
        when(employeeRepository.findById(99)).thenReturn(Optional.empty());

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> employeeService.deleteEmployeeById(99));

        // Assertions
        assertTrue(exception.getMessage().contains("Employee not found"));
        verify(employeeRepository, times(1)).findById(99);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Mock behavior
        when(employeeRepository.findByUsername("johndoe")).thenReturn(Optional.of(employee));

        // Test
        UserDetails result = employeeService.loadUserByUsername("johndoe");

        // Assertions
        assertNotNull(result);
        assertEquals(employee.getUsername(), result.getUsername());
        verify(employeeRepository, times(1)).findByUsername("johndoe");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Mock behavior
        when(employeeRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Test
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> employeeService.loadUserByUsername("nonexistent"));

        // Assertions
        assertTrue(exception.getMessage().contains("User not found"));
        verify(employeeRepository, times(1)).findByUsername("nonexistent");
    }
}
