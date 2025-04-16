package com.cognizant.employee_management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.dto.returnEmployeeDto;
import com.cognizant.employee_management.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	

	@GetMapping("/emp/getall")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<List<returnEmployeeDto>> getAllEmployees() {
		List<returnEmployeeDto> employees = employeeService.getAllEmployees();
//		System.out.println("Employees: " + employees);
		return ResponseEntity.ok(employees);
	}

	@PostMapping("/createEmployee")
	public ResponseEntity<EmployeeDto> createEmployees(@Valid @RequestBody EmployeeDto employeeDto) {
		EmployeeDto saved=employeeService.createEmployee(employeeDto);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable int id,@Valid @RequestBody EmployeeDto employeeDto) {
	    EmployeeDto updated = employeeService.updateEmployee(id, employeeDto);
	    return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/deleteEmployee/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
	    employeeService.deleteEmployee(id);
	    return ResponseEntity.ok("Employee deleted successfully.");
	}
	
}
