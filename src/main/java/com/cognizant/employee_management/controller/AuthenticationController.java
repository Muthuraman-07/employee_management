package com.cognizant.employee_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.AuthenticationRequestDto;
import com.cognizant.employee_management.dto.AuthenticationResponseDTO;
import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.service.AuthenticationService;
import com.cognizant.employee_management.service.EmployeeService;
import com.cognizant.employee_management.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AuthenticationService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> register(@Valid @RequestBody EmployeeDto employee) {
        log.info("[AUTHENTICATION-CONTROLLER] Registering new employee: {}", employee.getUsername());
        try {
            // Check if the employee ID already exists
            if (authService.existsById(employee.getEmployeeId())) {
                log.error("[AUTHENTICATION-CONTROLLER] Employee ID already exists: {}", employee.getEmployeeId());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee ID already exists: " + employee.getEmployeeId()); // Return 409 Conflict status with message
            }

            EmployeeDto savedEmployee = authService.save(employee);
            log.info("[AUTHENTICATION-CONTROLLER] Successfully registered employee: {}", employee.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee registered successfully with ID: " + savedEmployee.getEmployeeId());
        } catch (Exception e) {
            log.error("[AUTHENTICATION-CONTROLLER] Error registering employee: {}. Error: {}", employee.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering employee: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto request) {
        log.info("[AUTHENTICATION-CONTROLLER] Attempting login for username: {}", request.getUsername());
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Extract user role
            String role = userDetails.getAuthorities().stream().findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse(null);

            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails.getUsername(), role);
            log.info("[AUTHENTICATION-CONTROLLER] Login successful for username: {}", request.getUsername());
            return ResponseEntity.ok(new AuthenticationResponseDTO(token));
        } catch (BadCredentialsException ex) {
            log.warn("[AUTHENTICATION-CONTROLLER] Invalid login attempt for username: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        } catch (AuthenticationException ex) {
            log.error("[AUTHENTICATION-CONTROLLER] Authentication failed for username: {}. Error: {}", request.getUsername(), ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed: " + ex.getMessage());
        } catch (Exception e) {
            log.error("[AUTHENTICATION-CONTROLLER] Unexpected error during login for username: {}. Error: {}", request.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
