package com.cognizant.employee_management.service;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.dto.returnEmployeeDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<returnEmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, returnEmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee saved = employeeRepository.save(employee);

        return modelMapper.map(saved, EmployeeDto.class);
    }


    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existing.setUsername(employeeDto.getUsername());
        existing.setFirstName(employeeDto.getFirstName());
        existing.setLastName(employeeDto.getLastName());
        existing.setEmail(employeeDto.getEmail());
        existing.setPhoneNumber(employeeDto.getPhoneNumber());
        existing.setDepartment(employeeDto.getDepartment());
        existing.setRole(employeeDto.getRole());
        existing.setJoinedDate(employeeDto.getJoinedDate());
        existing.setManagerId(employeeDto.getManagerId());
        existing.setPassword(employeeDto.getPassword());
        existing.setShift(employeeDto.getShift());

        Employee updated = employeeRepository.save(existing);
        return modelMapper.map(updated, EmployeeDto.class);
    }
    
    @Override
    public void deleteEmployee(int id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                employee.getUsername(), employee.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(employee.getRole())));
    }

}
