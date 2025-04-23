package com.cognizant.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employeemanagement.dto.EmployeeDto;
import com.cognizant.employeemanagement.dto.ReturnEmployeeDto;
import com.cognizant.employeemanagement.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/employee")
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/get/all-employee-records")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<List<ReturnEmployeeDto>> getAllEmployees() {
		log.info("[EMPLOYEE-CONTROLLER] Fetching all employees");
		try {
			List<ReturnEmployeeDto> employees = employeeService.getAllEmployees();
			log.info("[EMPLOYEE-CONTROLLER] Successfully fetched {} employees", employees.size());
			return ResponseEntity.ok(employees);
		} catch (Exception e) {
			log.error("[EMPLOYEE-CONTROLLER] Error fetching employees: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PutMapping("/update/employee-record/{employeeId}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable int employeeId,
			@Valid @RequestBody EmployeeDto employeeDto) {
		log.info("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Updating employee details", employeeId);
		try {
			EmployeeDto updated = employeeService.updateEmployee(employeeId, employeeDto);
			log.info("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Successfully updated employee", employeeId);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			log.error("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Error updating employee: {}", employeeId, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@DeleteMapping("/delete/employee-record/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable int id) {
		log.info("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Deleting employee", id);
		try {
			employeeService.deleteEmployeeById(id);
			log.info("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Successfully deleted employee", id);
			return ResponseEntity.ok("Employee deleted successfully.");
		} catch (Exception e) {
			log.error("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Error deleting employee: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting employee: " + e.getMessage());
		}
	}
}
