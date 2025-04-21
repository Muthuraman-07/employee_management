package com.cognizant.employee_management.service;

import com.cognizant.employee_management.dto.ShiftRequestDto;

public interface ShiftRequestService {
    ShiftRequestDto requestShiftSwap(int employeeId, int shiftId);

    ShiftRequestDto approveShiftSwap(int requestId, boolean approved);
}
