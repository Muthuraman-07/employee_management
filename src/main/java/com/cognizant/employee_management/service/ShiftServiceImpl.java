package com.cognizant.employee_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.ReturnSwapRequestDTO;
import com.cognizant.employee_management.dto.ShiftDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.model.ShiftRequest;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.ShiftRepository;
import com.cognizant.employee_management.repository.ShiftRequestRepository;

@Service
public class ShiftServiceImpl implements ShiftService {

    private static final Logger log = LoggerFactory.getLogger(ShiftServiceImpl.class);

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShiftRequestRepository shiftRequestRepository;

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
            Shift shift = shiftRepository.findById(id).orElseThrow(() -> {
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
            return shifts.stream().map(shift -> modelMapper.map(shift, ShiftDto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[SHIFT-SERVICE] Error fetching all shifts. Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ShiftDto updateShift(int id, ShiftDto shiftDto) {
        log.info("[SHIFT-SERVICE] Updating shift with ID: {}", id);
        try {
            Shift existingShift = shiftRepository.findById(id).orElseThrow(() -> {
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

    @Override
    public ReturnSwapRequestDTO requestShiftSwap(int employeeId, int shiftId) {
        log.info("[SHIFT-SERVICE] Requesting shift swap for employee ID: {} and shift ID: {}", employeeId, shiftId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        ShiftRequest shiftRequest = new ShiftRequest();
        shiftRequest.setEmployee(employee);
        shiftRequest.setRequestedShift(shift);
        shiftRequest.setStatus("Pending");
        shiftRequest.setApprovedByManager(false);

        ShiftRequest savedRequest = shiftRequestRepository.save(shiftRequest);
        log.info("[SHIFT-SERVICE] Shift swap request created successfully with ID: {}", savedRequest.getId());
        return modelMapper.map(savedRequest, ReturnSwapRequestDTO.class);
    }

    @Override
    public ReturnSwapRequestDTO approveShiftSwap(int requestId, boolean status) {
        log.info("[SHIFT-SERVICE] Approving shift swap request ID: {}", requestId);
        ShiftRequest shiftRequest = shiftRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Shift request not found"));

        if (status) {
            shiftRequest.setStatus("APPROVED");
            
            Employee existingEmployee = employeeRepository.findById(shiftRequest.getEmployee().getEmployeeId())
                    .orElseThrow(() -> {
                        log.warn("[EMPLOYEE-SERVICE] Employee not found with ID: {}", shiftRequest.getEmployee().getEmployeeId());
                        return new RuntimeException("Employee not found with ID: " + shiftRequest.getEmployee().getEmployeeId());
                    });
            
            Shift shift = shiftRepository.findById(shiftRequest.getRequestedShift().getShiftId())
                    .orElseThrow(() -> {
                        log.warn("[EMPLOYEE-SERVICE] Invalid shift ID: {}",shiftRequest.getRequestedShift().getShiftId());
                        return new IllegalArgumentException("Invalid Shift ID: " +shiftRequest.getRequestedShift().getShiftId());
                    });
            
            existingEmployee.setShift(shift);
            employeeRepository.save(existingEmployee);
            shiftRequest.setApprovedByManager(true);
            
        } else {
            shiftRequest.setStatus("REJECTED");
        }

        ShiftRequest updatedRequest = shiftRequestRepository.save(shiftRequest);
        log.info("[SHIFT-SERVICE] Shift swap request ID: {} updated successfully", requestId);
        return modelMapper.map(updatedRequest, ReturnSwapRequestDTO.class);
    }
}
