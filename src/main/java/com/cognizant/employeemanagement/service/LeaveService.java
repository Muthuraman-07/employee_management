package com.cognizant.employeemanagement.service;

import java.util.List;

import com.cognizant.employeemanagement.dto.LeaveDto;
import com.cognizant.employeemanagement.dto.ReturnLeaveDto;
import com.cognizant.employeemanagement.model.Leave;

import jakarta.validation.Valid;

public interface LeaveService {
	List<ReturnLeaveDto> getAllLeaves();

	void deleteLeave(int id);

	Leave applyLeave(int employeeId, @Valid LeaveDto dto);

	List<ReturnLeaveDto> getAllPendingLeaveRequests(String status);
}