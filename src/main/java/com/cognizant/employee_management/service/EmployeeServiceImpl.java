package com.cognizant.employee_management.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.dto.returnEmployeeDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.repository.AttendanceRepository;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.repository.LeaveRepository;
import com.cognizant.employee_management.repository.ShiftRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public List<returnEmployeeDto> getAllEmployees() {
        log.info("[EMPLOYEE-SERVICE] Fetching all employees");
        try {
            List<Employee> employees = employeeRepository.findAll();
            log.info("[EMPLOYEE-SERVICE] Successfully fetched {} employees", employees.size());

            return employees.stream()
                .map(employee -> {
                    returnEmployeeDto dto = modelMapper.map(employee, returnEmployeeDto.class);
                    log.debug("[EMPLOYEE-SERVICE] Mapped DTO: {}", dto);
                    return dto;
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[EMPLOYEE-SERVICE] Error fetching employees: {}", e.getMessage(), e);
            throw e;
        }
    }

   

    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {
        log.info("[EMPLOYEE-SERVICE] Updating employee with ID: {}", id);
        try {
            Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[EMPLOYEE-SERVICE] Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found with ID: " + id);
                });

            Shift shift = shiftRepository.findById(employeeDto.getShiftId())
                .orElseThrow(() -> {
                    log.warn("[EMPLOYEE-SERVICE] Invalid shift ID: {}", employeeDto.getShiftId());
                    return new IllegalArgumentException("Invalid Shift ID: " + employeeDto.getShiftId());
                });

            existingEmployee.setUsername(employeeDto.getUsername());
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setEmail(employeeDto.getEmail());
            existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
            existingEmployee.setDepartment(employeeDto.getDepartment());
            existingEmployee.setRole(employeeDto.getRole());
            existingEmployee.setJoinedDate(employeeDto.getJoinedDate());
            existingEmployee.setManagerId(employeeDto.getManagerId());
            existingEmployee.setPassword(employeeDto.getPassword());
            existingEmployee.setShift(shift);

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            log.info("[EMPLOYEE-SERVICE] Employee with ID: {} updated successfully", id);

            return modelMapper.map(updatedEmployee, EmployeeDto.class);
        } catch (Exception e) {
            log.error("[EMPLOYEE-SERVICE] Error updating employee with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteEmployeeById(int id) {
        log.info("[EMPLOYEE-SERVICE] Deleting employee with ID: {}", id);
        try {
            Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[EMPLOYEE-SERVICE] Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found with ID: " + id);
                });

            attendanceRepository.deleteByEmployee_EmployeeId(employee.getEmployeeId());
            log.info("[EMPLOYEE-SERVICE] Deleted attendance records for employee ID: {}", employee.getEmployeeId());

            leaveRepository.deleteByEmployee_EmployeeId(employee.getEmployeeId());
            log.info("[EMPLOYEE-SERVICE] Deleted leave records for employee ID: {}", employee.getEmployeeId());

            leaveBalanceRepository.deleteByEmployee_EmployeeId(employee.getEmployeeId());
            log.info("[EMPLOYEE-SERVICE] Deleted leave balances for employee ID: {}", employee.getEmployeeId());

            employeeRepository.delete(employee);
            log.info("[EMPLOYEE-SERVICE] Employee with ID: {} deleted successfully", id);
        } catch (Exception e) {
            log.error("[EMPLOYEE-SERVICE] Error deleting employee with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[EMPLOYEE-SERVICE] Loading user by username: {}", username);
        try {
            Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("[EMPLOYEE-SERVICE] User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

            log.info("[EMPLOYEE-SERVICE] Successfully loaded user with username: {}", username);
            return new org.springframework.security.core.userdetails.User(
                employee.getUsername(), employee.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(employee.getRole()))
            );
        } catch (Exception e) {
            log.error("[EMPLOYEE-SERVICE] Error loading user by username: {}. Error: {}", username, e.getMessage(), e);
            throw e;
        }
    }
}
