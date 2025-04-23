package com.cognizant.employeemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognizant.employeemanagement.dto.EmployeeDto;
import com.cognizant.employeemanagement.model.Employee;
import com.cognizant.employeemanagement.model.LeaveBalance;
import com.cognizant.employeemanagement.repository.EmployeeRepository;
import com.cognizant.employeemanagement.repository.LeaveBalanceRepository;

@Service
public class AuthenticationService {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;

	public EmployeeDto save(EmployeeDto employeeDto) {
		log.info("[AUTHENTICATION-SERVICE] Saving new employee: {}", employeeDto.getUsername());
		try {
			// Check if the employee ID already exists
			if (employeeRepository.existsById(employeeDto.getEmployeeId())) {
				log.error("[AUTHENTICATION-SERVICE] Employee ID already exists: {}", employeeDto.getEmployeeId());
				throw new RuntimeException("Employee ID already exists: " + employeeDto.getEmployeeId());
			}

			Employee employee = modelMapper.map(employeeDto, Employee.class);
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));

			Employee savedEmployee = employeeRepository.save(employee);
			log.info("[AUTHENTICATION-SERVICE] Employee saved successfully with ID: {}", savedEmployee.getEmployeeId());

			List<LeaveBalance> leaveBalances = List.of(new LeaveBalance(savedEmployee, "Vacation", 10),
					new LeaveBalance(savedEmployee, "Sick Leave", 5),
					new LeaveBalance(savedEmployee, "Casual Leave", 7));

			leaveBalances.forEach(leaveBalance -> {
				leaveBalanceRepository.save(leaveBalance);
				log.info("[AUTHENTICATION-SERVICE] Leave balance '{}' created successfully for employee ID: {}",
						leaveBalance.getLeaveType(), savedEmployee.getEmployeeId());
			});

			log.info("[AUTHENTICATION-SERVICE] Returning saved employee details for ID: {}",
					savedEmployee.getEmployeeId());
			return modelMapper.map(savedEmployee, EmployeeDto.class);
		} catch (Exception e) {
			log.error("[AUTHENTICATION-SERVICE] Error saving employee: {}. Error: {}", employeeDto.getUsername(),
					e.getMessage(), e);
			throw e;
		}
	}

	public boolean existsById(int employeeId) {
		return employeeRepository.existsById(employeeId);
	}
}
