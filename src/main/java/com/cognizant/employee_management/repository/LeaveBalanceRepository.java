package com.cognizant.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.employee_management.model.LeaveBalance;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer>{

}
