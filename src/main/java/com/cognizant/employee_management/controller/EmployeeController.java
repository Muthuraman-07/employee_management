package com.cognizant.employee_management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.cognizant.employee_management.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/getall")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
		List<EmployeeDto> employees = employeeService.getAllEmployees();
//		System.out.println("Employees: " + employees);
		return ResponseEntity.ok(employees);
	}

	@PostMapping("/createEmployee")
	public ResponseEntity<EmployeeDto> createEmployees(@RequestBody EmployeeDto employeeDto) {
		EmployeeDto saved=employeeService.createEmployee(employeeDto);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable int id, @RequestBody EmployeeDto employeeDto) {
	    EmployeeDto updated = employeeService.updateEmployee(id, employeeDto);
	    return ResponseEntity.ok(updated);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<EmployeeDto> patchEmployee(
	        @PathVariable int id,
	        @RequestBody Map<String, Object> updates) {
	    EmployeeDto patched = employeeService.patchEmployee(id, updates);
	    return ResponseEntity.ok(patched);
	
	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
	    employeeService.deleteEmployee(id);
	    return ResponseEntity.ok("Employee deleted successfully.");
	}
	
}
