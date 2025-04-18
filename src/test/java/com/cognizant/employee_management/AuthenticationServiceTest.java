
//package com.cognizant.employee_management;
//
//public class AuthenticationServiceTest {
//
//}
package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.service.AuthenticationService;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthenticationService authenticationService;

  private Employee employee;

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
  }

  @Test
  void testSave() {
      when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword123");
      when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

      Employee savedEmployee = authenticationService.save(employee);

      assertNotNull(savedEmployee);
      verify(passwordEncoder).encode("password123");
      verify(employeeRepository).save(employee);
  }
}
