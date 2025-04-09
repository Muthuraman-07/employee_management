package com.cognizant.employee_management.service;

import java.util.List;
import java.util.Map;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.dto.returnleavedto;

public interface LeaveService {
    List<returnleavedto> getAllLeaves();
    LeaveDto getLeaveById(int id);
    LeaveDto createLeave(LeaveDto leaveDto);
    LeaveDto updateLeave(int id, LeaveDto leaveDto);
    LeaveDto patchLeave(int id, Map<String, Object> updates);
    void deleteLeave(int id);
	void applyLeave(int id, LeaveDto leaveDto);
}