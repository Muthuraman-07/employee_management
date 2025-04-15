package com.cognizant.employee_management.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.employee_management.dto.EmployeeDto;

public interface EmployeeService {
	List<EmployeeDto> getAllEmployees();
	EmployeeDto createEmployee(EmployeeDto employeeDto);
	EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);
	EmployeeDto patchEmployee(int id,Map<String, Object> updates);
	void deleteEmployee(int id);
	UserDetails loadUserByUsername(String username);
}
