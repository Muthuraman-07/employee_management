package com.cognizant.employee_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.ShiftDto;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.repository.ShiftRepository;

@Service
public class ShiftServiceImpl implements ShiftService {

    private static final Logger log = LoggerFactory.getLogger(ShiftServiceImpl.class);

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ShiftDto createShift(ShiftDto shiftDto) {
        log.info("[SHIFT-SERVICE] Creating a new shift");
        try {
            Shift shift = modelMapper.map(shiftDto, Shift.class);
            Shift savedShift = shiftRepository.save(shift);
            log.info("[SHIFT-SERVICE] Shift created successfully with ID: {}", savedShift.getShiftId());
            return modelMapper.map(savedShift, ShiftDto.class);
        } catch (Exception e) {
            log.error("[SHIFT-SERVICE] Error creating shift. Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ShiftDto getShiftById(int id) {
        log.info("[SHIFT-SERVICE] Fetching shift with ID: {}", id);
        try {
            Shift shift = shiftRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("[SHIFT-SERVICE] Shift not found with ID: {}", id);
                        return new RuntimeException("Shift not found");
                    });
            log.info("[SHIFT-SERVICE] Successfully fetched shift with ID: {}", id);
            return modelMapper.map(shift, ShiftDto.class);
        } catch (Exception e) {
            log.error("[SHIFT-SERVICE] Error fetching shift with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ShiftDto> getAllShifts() {
        log.info("[SHIFT-SERVICE] Fetching all shifts");
        try {
            List<Shift> shifts = shiftRepository.findAll();
            log.info("[SHIFT-SERVICE] Successfully fetched {} shifts", shifts.size());
            return shifts.stream()
                    .map(shift -> modelMapper.map(shift, ShiftDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[SHIFT-SERVICE] Error fetching all shifts. Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ShiftDto updateShift(int id, ShiftDto shiftDto) {
        log.info("[SHIFT-SERVICE] Updating shift with ID: {}", id);
        try {
            Shift existingShift = shiftRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("[SHIFT-SERVICE] Shift not found with ID: {}", id);
                        return new RuntimeException("Shift not found");
                    });

            existingShift.setShiftDate(shiftDto.getShiftDate());
            existingShift.setShiftStartTime(shiftDto.getShiftStartTime());
            existingShift.setShiftEndTime(shiftDto.getShiftEndTime());

            Shift updatedShift = shiftRepository.save(existingShift);
            log.info("[SHIFT-SERVICE] Shift with ID: {} updated successfully", id);
            return modelMapper.map(updatedShift, ShiftDto.class);
        } catch (Exception e) {
            log.error("[SHIFT-SERVICE] Error updating shift with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ShiftDto patchShift(int id, ShiftDto shiftDto) {
        log.info("[SHIFT-SERVICE] Patching shift with ID: {}", id);
        try {
            Shift existingShift = shiftRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("[SHIFT-SERVICE] Shift not found with ID: {}", id);
                        return new RuntimeException("Shift not found");
                    });

            if (shiftDto.getShiftDate() != null) {
                existingShift.setShiftDate(shiftDto.getShiftDate());
            }
            if (shiftDto.getShiftStartTime() != null) {
                existingShift.setShiftStartTime(shiftDto.getShiftStartTime());
            }
            if (shiftDto.getShiftEndTime() != null) {
                existingShift.setShiftEndTime(shiftDto.getShiftEndTime());
            }

            Shift patchedShift = shiftRepository.save(existingShift);
            log.info("[SHIFT-SERVICE] Shift with ID: {} patched successfully", id);
            return modelMapper.map(patchedShift, ShiftDto.class);
        } catch (Exception e) {
            log.error("[SHIFT-SERVICE] Error patching shift with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteShift(int id) {
        log.info("[SHIFT-SERVICE] Deleting shift with ID: {}", id);
        try {
            if (!shiftRepository.existsById(id)) {
                log.warn("[SHIFT-SERVICE] Shift not found with ID: {}", id);
                throw new RuntimeException("Shift not found with ID: " + id);
            }
            shiftRepository.deleteById(id);
            log.info("[SHIFT-SERVICE] Shift with ID: {} deleted successfully", id);
        } catch (Exception e) {
            log.error("[SHIFT-SERVICE] Error deleting shift with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
