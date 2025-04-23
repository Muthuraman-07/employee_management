package com.cognizant.employeemanagement.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.employeemanagement.model.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	void deleteByEmployee_EmployeeId(int employeeId);

	boolean existsByEmployeeEmployeeIdAndClockInTimeBetween(int id, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
