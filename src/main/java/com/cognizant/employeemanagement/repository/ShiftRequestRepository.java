package com.cognizant.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.employeemanagement.model.ShiftRequest;

@Repository
public interface ShiftRequestRepository extends JpaRepository<ShiftRequest, Integer> {
}