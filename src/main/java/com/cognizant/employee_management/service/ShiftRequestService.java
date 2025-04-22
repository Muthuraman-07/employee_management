package com.cognizant.employee_management.service;

import com.cognizant.employee_management.dto.ReturnSwapRequestDTO;

public interface ShiftRequestService {
	ReturnSwapRequestDTO requestShiftSwap(int employeeId, int shiftId);

	ReturnSwapRequestDTO approveShiftSwap(int requestId, boolean approved);
}
