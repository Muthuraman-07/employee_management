package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.dto.returnLeaveDto;
import com.cognizant.employee_management.model.Leave;

import jakarta.validation.Valid;

public interface LeaveService {
	List<returnLeaveDto> getAllLeaves();

	void deleteLeave(int id);

	Leave applyLeave(int employeeId, @Valid LeaveDto dto);

	List<returnLeaveDto> getAllPendingLeaveRequests(String status);
}