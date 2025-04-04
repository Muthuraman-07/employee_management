package com.cognizant.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.employee_management.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
//	List<Employee> findAll();
}
