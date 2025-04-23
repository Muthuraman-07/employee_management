package com.cognizant.employeemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.employeemanagement.model.Leave;
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer> {
	List<Leave> findByStatus(String string);

	void deleteByEmployee_EmployeeId(int employeeId);
}
