package com.cognizant.employeemanagement.service;

import com.cognizant.employeemanagement.dto.ReturnSwapRequestDTO;

public interface ShiftRequestService {
	ReturnSwapRequestDTO requestShiftSwap(int employeeId, int shiftId);

	ReturnSwapRequestDTO approveShiftSwap(int requestId, boolean approved);
}
