package com.cognizant.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.employeemanagement.model.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {

}
