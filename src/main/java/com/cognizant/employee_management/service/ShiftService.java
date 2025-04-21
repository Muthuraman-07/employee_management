package com.cognizant.employee_management.service;

import java.util.List;

import com.cognizant.employee_management.dto.ShiftDto;

public interface ShiftService {
    ShiftDto createShift(ShiftDto shiftDto);

    ShiftDto getShiftById(int id);

    List<ShiftDto> getAllShifts();

    ShiftDto updateShift(int id, ShiftDto shiftDto);

    void deleteShift(int id);

    
}
