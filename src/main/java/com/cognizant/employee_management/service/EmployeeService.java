package com.cognizant.employee_management.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.dto.returnEmployeeDto;

public interface EmployeeService {
	List<returnEmployeeDto> getAllEmployees();
	
	EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);
	void deleteEmployeeById(int id);
	UserDetails loadUserByUsername(String username);
}
