package com.cognizant.employee_management.service;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
	@Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;
    
	@Override
    public List<EmployeeDto> getAllEmployees() {
		List<Employee> employees=employeeRepository.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }
	
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto)
	{
		Employee employee=modelMapper.map(employeeDto,Employee.class);
		Employee saved=employeeRepository.save(employee);
		return modelMapper.map(saved, EmployeeDto.class);
	}
}
