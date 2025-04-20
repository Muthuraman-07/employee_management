package com.cognizant.employee_management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cognizant.employee_management.model.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Integer>{
	
	


}
