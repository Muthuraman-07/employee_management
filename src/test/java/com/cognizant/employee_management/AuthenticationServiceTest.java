package com.cognizant.employee_management;
 
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
 
import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.service.AuthenticationService;
import org.modelmapper.ModelMapper;
 
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
 
    @Mock
    private EmployeeRepository employeeRepository;
 
    @Mock
    private PasswordEncoder passwordEncoder;
 
    @Mock
    private ModelMapper modelMapper;
 
    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;
 
    @InjectMocks
    private AuthenticationService authenticationService;
 
    private Employee employee;
    private EmployeeDto employeeDto;
 
    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmployeeId(1);
        employee.setUsername("testuser");
        employee.setPassword("password123");
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setEmail("testuser@example.com");
        employee.setPhoneNumber("1234567890");
        employee.setDepartment("IT");
        employee.setRole("Developer");
 
        employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(1);
        employeeDto.setUsername("testuser");
        employeeDto.setPassword("password123");
        employeeDto.setFirstName("Test");
        employeeDto.setLastName("User");
        employeeDto.setEmail("testuser@example.com");
        employeeDto.setPhoneNumber("1234567890");
        employeeDto.setDepartment("IT");
        employeeDto.setRole("Developer");
    }
 
    @Test
    void testSave() {
        when(modelMapper.map(any(EmployeeDto.class), eq(Employee.class))).thenReturn(employee);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword123");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(any(Employee.class), eq(EmployeeDto.class))).thenReturn(employeeDto);
 
        EmployeeDto savedEmployeeDto = authenticationService.save(employeeDto);
 
        assertNotNull(savedEmployeeDto);
        verify(modelMapper).map(employeeDto, Employee.class);
        verify(passwordEncoder).encode("password123");
        verify(employeeRepository).save(employee);
        verify(modelMapper).map(employee, EmployeeDto.class);
        verify(leaveBalanceRepository, times(3)).save(any(LeaveBalance.class));
    }
}