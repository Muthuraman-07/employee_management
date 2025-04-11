package com.cognizant.employee_management.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.employee_management.dto.EmployeeDto;

public interface EmployeeService {
	List<EmployeeDto> getAllEmployees();
	EmployeeDto createEmployee(EmployeeDto employeeDto);
	EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);
	void deleteEmployee(int id);
	UserDetails loadUserByUsername(String username);
}
