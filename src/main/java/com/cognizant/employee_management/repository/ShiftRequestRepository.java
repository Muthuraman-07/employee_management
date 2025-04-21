package com.cognizant.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cognizant.employee_management.model.ShiftRequest;

public interface ShiftRequestRepository extends JpaRepository<ShiftRequest, Integer> {
}
