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
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.service.AuthenticationService;
import com.cognizant.employee_management.service.EmployeeService;
import com.cognizant.employee_management.util.JwtUtil;

import jakarta.validation.Valid;


@RestController

@RequestMapping("/api/auth")
public class AuthenticationController {
	@Autowired
	EmployeeService employeeService;

	@Autowired
	AuthenticationService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	

	@PostMapping("/saveEmployee")
	public Employee register(@Valid @RequestBody Employee employee) {
		return authService.save(employee);
	}

	@PostMapping("/login")

	public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto request) {

		try {
			Authentication authentication = authenticationManager.authenticate(

					new UsernamePasswordAuthenticationToken

					(request.getUsername(), request.getPassword()));
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			String role = userDetails.getAuthorities().stream().findFirst().
					map(GrantedAuthority::getAuthority)
					.orElse(null);
			String token = jwtUtil.generateToken(userDetails.getUsername(), role);
			return ResponseEntity.ok(new AuthenticationResponseDTO(token));
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
					body("Invalid username or password");
		} catch (AuthenticationException ex) {
			return ResponseEntity.
					status(HttpStatus.UNAUTHORIZED).body("Authentication failed " + ex.getMessage());
		}

	}

}
