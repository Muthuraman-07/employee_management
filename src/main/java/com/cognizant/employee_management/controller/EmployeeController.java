package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/getall")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
		List<EmployeeDto> employees = employeeService.getAllEmployees();
		System.out.println("Employees: " + employees); // Prints in console
		return ResponseEntity.ok(employees);
	}

	@PostMapping("/createEmployee")
	public ResponseEntity<EmployeeDto> createEmployees(@RequestBody EmployeeDto employeeDto) {
		EmployeeDto saved=employeeService.createEmployee(employeeDto);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
}
