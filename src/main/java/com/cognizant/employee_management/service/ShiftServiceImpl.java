package com.cognizant.employee_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.employee_management.dto.ShiftDto;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.repository.ShiftRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ShiftDto createShift(@Valid @NotNull ShiftDto shiftDto) {
        Shift shift = modelMapper.map(shiftDto, Shift.class);
        Shift saved = shiftRepository.save(shift);
        return modelMapper.map(saved, ShiftDto.class);
    }

    @Override
    public ShiftDto getShiftById(int id) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        return modelMapper.map(shift, ShiftDto.class);
    }

    @Override
    public List<ShiftDto> getAllShifts() {
        return shiftRepository.findAll().stream()
                .map(shift -> modelMapper.map(shift, ShiftDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShiftDto updateShift(int id, @Valid @NotNull ShiftDto shiftDto) {
        Shift existing = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        existing.setShiftDate(shiftDto.getShiftDate());
        existing.setShiftStartTime(shiftDto.getShiftStartTime());
        existing.setShiftEndTime(shiftDto.getShiftEndTime());

        Shift updated = shiftRepository.save(existing);
        return modelMapper.map(updated, ShiftDto.class);
    }

    @Override
    @Transactional
    public ShiftDto patchShift(int id,@NotNull ShiftDto shiftDto) {
        Shift existing = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        if (shiftDto.getShiftDate() != null) {
            existing.setShiftDate(shiftDto.getShiftDate());
        }
        if (shiftDto.getShiftStartTime() != null) {
            existing.setShiftStartTime(shiftDto.getShiftStartTime());
        }
        if (shiftDto.getShiftEndTime() != null) {
            existing.setShiftEndTime(shiftDto.getShiftEndTime());
        }

        Shift patched = shiftRepository.save(existing);
        return modelMapper.map(patched, ShiftDto.class);
    }

    @Override
    @Transactional
    public void deleteShift(int id) {
        if (!shiftRepository.existsById(id)) {
            throw new RuntimeException("Shift not found");
        }
        shiftRepository.deleteById(id);
    }
}
