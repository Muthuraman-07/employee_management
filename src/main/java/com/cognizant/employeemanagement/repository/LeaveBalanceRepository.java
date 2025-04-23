package com.cognizant.employeemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.employeemanagement.model.Employee;
import com.cognizant.employeemanagement.model.LeaveBalance;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer>{
	LeaveBalance findByEmployeeAndLeaveType(Employee employee, String leaveType);

	List<LeaveBalance> findByEmployee(Employee employee);
//	void deleteByEmployeeId(int employeeId);

    void deleteByEmployee_EmployeeId(int employeeId);
}
