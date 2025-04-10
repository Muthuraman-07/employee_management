package com.cognizant.employee_management.service;

import java.util.List;
import java.util.Map;

import com.cognizant.employee_management.dto.EmployeeDto;

public interface EmployeeService {
	List<EmployeeDto> getAllEmployees();
	EmployeeDto createEmployee(EmployeeDto employeeDto);
	EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);
	void deleteEmployee(int id);
}
