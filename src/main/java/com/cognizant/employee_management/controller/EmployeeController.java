package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.dto.returnEmployeeDto;
import com.cognizant.employee_management.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emp/getall")
//    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<returnEmployeeDto>> getAllEmployees() {
        log.info("[EMPLOYEE-CONTROLLER] Fetching all employees");
        try {
            List<returnEmployeeDto> employees = employeeService.getAllEmployees();
            log.info("[EMPLOYEE-CONTROLLER] Successfully fetched {} employees", employees.size());
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            log.error("[EMPLOYEE-CONTROLLER] Error fetching employees: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

   

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable int id, @Valid @RequestBody EmployeeDto employeeDto) {
        log.info("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Updating employee details", id);
        try {
            EmployeeDto updated = employeeService.updateEmployee(id, employeeDto);
            log.info("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Successfully updated employee", id);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Error updating employee: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable int id) {
        log.info("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Deleting employee", id);
        try {
            employeeService.deleteEmployeeById(id);
            log.info("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Successfully deleted employee", id);
            return ResponseEntity.ok("Employee deleted successfully.");
        } catch (Exception e) {
            log.error("[EMPLOYEE-CONTROLLER][Employee-ID: {}] Error deleting employee: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting employee: " + e.getMessage());
        }
    }
}
