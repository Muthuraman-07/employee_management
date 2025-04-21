package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.dto.LeaveBalanceDto;

public interface LeaveBalanceService {
	LeaveBalanceDto createLeaveBalance(LeaveBalanceDto leaveBalanceDto);

	List<LeaveBalanceDto> getAllLeaveBalances();

	void deleteLeaveBalance(int id);
}
