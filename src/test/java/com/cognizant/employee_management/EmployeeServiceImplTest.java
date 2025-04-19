//package com.cognizant.employee_management;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.cognizant.employee_management.dto.EmployeeDto;
//import com.cognizant.employee_management.dto.returnEmployeeDto;
//import com.cognizant.employee_management.model.Employee;
//import com.cognizant.employee_management.model.Shift;
//import com.cognizant.employee_management.repository.EmployeeRepository;
//import com.cognizant.employee_management.service.EmployeeServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class EmployeeServiceImplTest {
//
//    @Mock
//    private EmployeeRepository employeeRepository;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private EmployeeServiceImpl employeeService;
//
//    private Employee employee;
//    private EmployeeDto employeeDto;
//    private returnEmployeeDto returnEmployeeDto;
//
//    @BeforeEach
//    void setUp() {
//        employee = new Employee();
//        employee.setEmployeeId(1);
//        employee.setUsername("john_doe");
//        employee.setPassword("Password123");
//        employee.setFirstName("John");
//        employee.setLastName("Doe");
//        employee.setEmail("john.doe@example.com");
//        employee.setPhoneNumber("1234567890");
//        employee.setDepartment("IT");
//        employee.setRole("USER");
//        employee.setShift(Shift.MORNING);
//        employee.setJoinedDate(LocalDate.of(2020, 1, 1));
//        employee.setManagerId(2);
//
//        employeeDto = new EmployeeDto();
//        employeeDto.setEmployeeId(1);
//        employeeDto.setUsername("john_doe");
//        employeeDto.setPassword("Password123");
//        employeeDto.setFirstName("John");
//        employeeDto.setLastName("Doe");
//        employeeDto.setEmail("john.doe@example.com");
//        employeeDto.setPhoneNumber("1234567890");
//        employeeDto.setDepartment("IT");
//        employeeDto.setRole("USER");
//        employeeDto.setShift(Shift.MORNING);
//        employeeDto.setJoinedDate(LocalDate.of(2020, 1, 1));
//        employeeDto.setManagerId(2);
//
//        returnEmployeeDto = new returnEmployeeDto();
//        returnEmployeeDto.setEmployeeId(1);
//        returnEmployeeDto.setFirstName("john_doe");
//        returnEmployeeDto.setFirstName("John");
//        returnEmployeeDto.setLastName("Doe");
//        returnEmployeeDto.setEmail("john.doe@example.com");
//        returnEmployeeDto.setPhoneNumber("1234567890");
//        returnEmployeeDto.setDepartment("IT");
//        returnEmployeeDto.setRole("USER");
//        returnEmployeeDto.setShift(Shift.MORNING);
//        returnEmployeeDto.setJoinedDate(LocalDate.of(2020, 1, 1));
//        returnEmployeeDto.setManagerId(2);
//    }
//
//    @Test
//    void testGetAllEmployees() {
//        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
//        when(modelMapper.map(employee, returnEmployeeDto.class)).thenReturn(returnEmployeeDto);
//
//        List<returnEmployeeDto> result = employeeService.getAllEmployees();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(returnEmployeeDto, result.get(0));
//        verify(employeeRepository, times(1)).findAll();
//    }
//    @Test
//    void testCreateEmployee() {
//        // Mock the model mapper to map EmployeeDto to Employee
//        when(modelMapper.map(employeeDto, Employee.class)).thenReturn(employee);
//        
//        // Mock the repository to save the employee
//        when(employeeRepository.save(employee)).thenReturn(employee);
//        
//        // Mock the model mapper to map Employee to EmployeeDto
//        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);
//
//        // Call the service method
//        EmployeeDto result = employeeService.createEmployee(employeeDto);
//
//        // Verify the results
//        assertNotNull(result);
//        assertEquals(employeeDto, result);
//        
//        // Verify the interactions
//        verify(employeeRepository, times(1)).save(employee);
//        verify(modelMapper, times(1)).map(employeeDto, Employee.class);
//        verify(modelMapper, times(1)).map(employee, EmployeeDto.class);
//    }
//
//
////    @Test
////    void testCreateEmployee() {
////        when(modelMapper.map(employeeDto, Employee.class)).thenReturn(employee);
////        when(employeeRepository.save(employee)).thenReturn(employee);
////        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);
////
////        EmployeeDto result = employeeService.createEmployee(employeeDto);
////
////        assertEquals(employeeDto, result);
////        verify(employeeRepository, times(1)).save(employee);
////    }
//
//    @Test
//    void testUpdateEmployee() {
//        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
//        when(employeeRepository.save(employee)).thenReturn(employee);
//        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);
//
//        EmployeeDto result = employeeService.updateEmployee(1, employeeDto);
//
//        assertEquals(employeeDto, result);
//        verify(employeeRepository, times(1)).findById(1);
//        verify(employeeRepository, times(1)).save(employee);
//    }
//
//    @Test
//    void testDeleteEmployee() {
//        when(employeeRepository.existsById(1)).thenReturn(true);
//
//        employeeService.deleteEmployee(1);
//
//        verify(employeeRepository, times(1)).existsById(1);
//        verify(employeeRepository, times(1)).deleteById(1);
//    }
//
//    @Test
//    void testLoadUserByUsername() {
//        when(employeeRepository.findByUsername("john_doe")).thenReturn(Optional.of(employee));
//
//        UserDetails userDetails = employeeService.loadUserByUsername("john_doe");
//
//        assertNotNull(userDetails);
//        assertEquals("john_doe", userDetails.getUsername());
//        verify(employeeRepository, times(1)).findByUsername("john_doe");
//    }
//
//    @Test
//    void testLoadUserByUsernameNotFound() {
//        when(employeeRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> {
//            employeeService.loadUserByUsername("unknown_user");
//        });
//
//        verify(employeeRepository, times(1)).findByUsername("unknown_user");
//    }
//}
//
////package com.cognizant.employee_management;
////
////import static org.junit.jupiter.api.Assertions.*;
////import static org.mockito.Mockito.*;
////
////import java.time.LocalDate;
////import java.util.Arrays;
////import java.util.List;
////import java.util.Optional;
////
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.junit.jupiter.MockitoExtension;
////import org.modelmapper.ModelMapper;
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.security.core.userdetails.UsernameNotFoundException;
////
////import com.cognizant.employee_management.dto.EmployeeDto;
////import com.cognizant.employee_management.dto.returnEmployeeDto;
////import com.cognizant.employee_management.model.Employee;
////import com.cognizant.employee_management.model.Shift;
////import com.cognizant.employee_management.repository.EmployeeRepository;
////import com.cognizant.employee_management.service.EmployeeServiceImpl;
////
////@ExtendWith(MockitoExtension.class)
////public class EmployeeServiceImplTest {
////
////    @Mock
////    private EmployeeRepository employeeRepository;
////
////    @Mock
////    private ModelMapper modelMapper;
////
////    @InjectMocks
////    private EmployeeServiceImpl employeeService;
////
////    private Employee employee;
////    private EmployeeDto employeeDto;
////
////    @BeforeEach
////    void setUp() {
////        employee = new Employee();
////        employee.setEmployeeId(1);
////        employee.setUsername("john_doe");
////        employee.setPassword("Password123");
////        employee.setFirstName("John");
////        employee.setLastName("Doe");
////        employee.setEmail("john.doe@example.com");
////        employee.setPhoneNumber("1234567890");
////        employee.setDepartment("IT");
////        employee.setRole("USER");
////        employee.setShift(Shift.MORNING);
////        employee.setJoinedDate(LocalDate.of(2020, 1, 1));
////        employee.setManagerId(2);
////
////        employeeDto = new EmployeeDto();
////        employeeDto.setEmployeeId(1);
////        employeeDto.setUsername("john_doe");
////        employeeDto.setPassword("Password123");
////        employeeDto.setFirstName("John");
////        employeeDto.setLastName("Doe");
////        employeeDto.setEmail("john.doe@example.com");
////        employeeDto.setPhoneNumber("1234567890");
////        employeeDto.setDepartment("IT");
////        employeeDto.setRole("USER");
////        employeeDto.setShift(Shift.MORNING);
////        employeeDto.setJoinedDate(LocalDate.of(2020, 1, 1));
////        employeeDto.setManagerId(2);
////    }
////
////    @Test
////    void testGetAllEmployees() {
////        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
////        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);
////
////        List<returnEmployeeDto> result = employeeService.getAllEmployees();
////
////        assertEquals(1, result.size());
////        assertEquals(employeeDto, result.get(0));
////        verify(employeeRepository, times(1)).findAll();
////    }
////
////    @Test
////    void testCreateEmployee() {
////        when(modelMapper.map(employeeDto, Employee.class)).thenReturn(employee);
////        when(employeeRepository.save(employee)).thenReturn(employee);
////        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);
////
////        EmployeeDto result = employeeService.createEmployee(employeeDto);
////
////        assertEquals(employeeDto, result);
////        verify(employeeRepository, times(1)).save(employee);
////    }
////
////    @Test
////    void testUpdateEmployee() {
////        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
////        when(employeeRepository.save(employee)).thenReturn(employee);
////        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);
////
////        EmployeeDto result = employeeService.updateEmployee(1, employeeDto);
////
////        assertEquals(employeeDto, result);
////        verify(employeeRepository, times(1)).findById(1);
////        verify(employeeRepository, times(1)).save(employee);
////    }
////
////    @Test
////    void testDeleteEmployee() {
////        when(employeeRepository.existsById(1)).thenReturn(true);
////
////        employeeService.deleteEmployee(1);
////
////        verify(employeeRepository, times(1)).existsById(1);
////        verify(employeeRepository, times(1)).deleteById(1);
////    }
////
////    @Test
////    void testLoadUserByUsername() {
////        when(employeeRepository.findByUsername("john_doe")).thenReturn(Optional.of(employee));
////
////        UserDetails userDetails = employeeService.loadUserByUsername("john_doe");
////
////        assertNotNull(userDetails);
////        assertEquals("john_doe", userDetails.getUsername());
////        verify(employeeRepository, times(1)).findByUsername("john_doe");
////    }
////
////    @Test
////    void testLoadUserByUsernameNotFound() {
////        when(employeeRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());
////
////        assertThrows(UsernameNotFoundException.class, () -> {
////            employeeService.loadUserByUsername("unknown_user");
////        });
////
////        verify(employeeRepository, times(1)).findByUsername("unknown_user");
////    }
////}
