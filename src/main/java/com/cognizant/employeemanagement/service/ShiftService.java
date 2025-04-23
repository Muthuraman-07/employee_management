package com.cognizant.employeemanagement.service;

import java.util.List;

import com.cognizant.employeemanagement.dto.ReturnSwapRequestDTO;
import com.cognizant.employeemanagement.dto.ShiftDto;

public interface ShiftService {
    ShiftDto createShift(ShiftDto shiftDto);

    ShiftDto getShiftById(int id);

    List<ShiftDto> getAllShifts();

    ShiftDto updateShift(int id, ShiftDto shiftDto);

    void deleteShift(int id);

    ReturnSwapRequestDTO requestShiftSwap(int employeeId, int shiftId);

    ReturnSwapRequestDTO approveShiftSwap(int requestId, boolean approved);
}
