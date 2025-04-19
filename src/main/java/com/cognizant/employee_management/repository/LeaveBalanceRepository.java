package com.cognizant.employee_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.LeaveBalance;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer>{
	LeaveBalance findByEmployeeAndLeaveType(Employee employee, String leaveType);

	List<LeaveBalance> findByEmployee(Employee employee);
//	void deleteByEmployeeId(int employeeId);

    void deleteByEmployee_EmployeeId(int employeeId);
}
