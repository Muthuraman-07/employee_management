package com.cognizant.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.employee_management.model.Leave;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer>{

}
