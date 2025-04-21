package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.dto.ShiftDto;
import com.cognizant.employee_management.dto.ShiftRequestDto;

public interface ShiftService {
    ShiftDto createShift(ShiftDto shiftDto);

    ShiftDto getShiftById(int id);

    List<ShiftDto> getAllShifts();

    ShiftDto updateShift(int id, ShiftDto shiftDto);

    void deleteShift(int id);

    ShiftRequestDto requestShiftSwap(int employeeId, int shiftId);

    ShiftRequestDto approveShiftSwap(int requestId, boolean approved);
}
