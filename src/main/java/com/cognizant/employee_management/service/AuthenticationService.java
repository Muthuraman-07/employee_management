package com.cognizant.employee_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.repository.LeaveRepository;

@Service
public class AuthenticationService {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

//	public Employee save(Employee employee) {
//		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
//		return employeeRepository.save(employee);
//	}
	
	public EmployeeDto save(EmployeeDto employeeDto) {
		Employee employee= modelMapper.map(employeeDto,Employee.class);
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		Employee saved=employeeRepository.save(employee);
		List<LeaveBalance> leaveBalances = List.of(
	            new LeaveBalance(saved, "Vacation", 10),
	            new LeaveBalance(saved, "Sick Leave", 5),
	            new LeaveBalance(saved, "Casual Leave", 7)
	        );

	        leaveBalances.forEach(leaveBalanceRepository::save);
		return modelMapper.map(saved, EmployeeDto.class);
	}
}
