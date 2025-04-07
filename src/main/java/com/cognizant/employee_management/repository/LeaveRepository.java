package com.cognizant.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.employee_management.model.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer>{

}
