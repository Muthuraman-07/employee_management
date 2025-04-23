package com.cognizant.employeemanagement.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.employeemanagement.dto.EmployeeDto;
import com.cognizant.employeemanagement.dto.ReturnEmployeeDto;

public interface EmployeeService {
	List<ReturnEmployeeDto> getAllEmployees();

	EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);

	void deleteEmployeeById(int id);

	UserDetails loadUserByUsername(String username);
}
