package com.cognizant.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.employee_management.model.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Integer>{
	


}
