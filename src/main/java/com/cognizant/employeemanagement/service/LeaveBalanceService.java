package com.cognizant.employeemanagement.service;

import java.util.List;

import com.cognizant.employeemanagement.dto.LeaveBalanceDto;

public interface LeaveBalanceService {
	LeaveBalanceDto createLeaveBalance(LeaveBalanceDto leaveBalanceDto);

	List<LeaveBalanceDto> getAllLeaveBalances();

	void deleteLeaveBalance(int id);
}
