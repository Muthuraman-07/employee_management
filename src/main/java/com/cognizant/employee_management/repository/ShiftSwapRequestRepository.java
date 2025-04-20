package com.cognizant.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cognizant.employee_management.model.ShiftSwapRequest;
import java.util.List;

public interface ShiftSwapRequestRepository extends JpaRepository<ShiftSwapRequest, Integer> {
    List<ShiftSwapRequest> findByRequesterId(int requesterId);
    List<ShiftSwapRequest> findByApproverId(int approverId);
    List<ShiftSwapRequest> findByStatus(String status);
}
