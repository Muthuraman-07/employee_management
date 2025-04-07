package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.dto.LeaveBalanceDto;

public interface LeaveBalanceService {
	LeaveBalanceDto createLeaveBalance(LeaveBalanceDto leaveBalanceDto);
    LeaveBalanceDto getLeaveBalanceById(int id);
    List<LeaveBalanceDto> getAllLeaveBalances();
    LeaveBalanceDto updateLeaveBalance(int id, LeaveBalanceDto leaveBalanceDto);
    LeaveBalanceDto patchLeaveBalance(int id, LeaveBalanceDto leaveBalanceDto);
    void deleteLeaveBalance(int id);
}
