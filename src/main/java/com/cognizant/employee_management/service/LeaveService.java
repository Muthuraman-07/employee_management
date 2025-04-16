package com.cognizant.employee_management.service;

import java.util.List;
import java.util.Map;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.dto.returnLeaveDto;
import com.cognizant.employee_management.model.Leave;

import jakarta.validation.Valid;

public interface LeaveService {
    List<LeaveDto> getAllLeaves();
    LeaveDto getLeaveById(int id);
    LeaveDto createLeave(LeaveDto leaveDto);
    LeaveDto updateLeave(int id, LeaveDto leaveDto);
    void deleteLeave(int id);
	void applyLeave(int employeeId, @Valid LeaveDto dto);
	List<returnLeaveDto> getAllPendingLeaveRequests(String status);
}