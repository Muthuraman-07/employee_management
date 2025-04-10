package com.cognizant.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.LeaveBalance;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer>{
	LeaveBalance findByEmployeeAndLeaveType(Employee employee, String leaveType);
}
