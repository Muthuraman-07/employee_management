package com.cognizant.employee_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.repository.EmployeeRepository;

@Service
public class AuthenticationService {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public Employee save(Employee employee) {
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		return employeeRepository.save(employee);
	}
}
