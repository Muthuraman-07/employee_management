package com.cognizant.employee_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.employee_management.model.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer>{
	
	List<Attendance> findAll();
	Optional<Attendance> findById(int id);
}
