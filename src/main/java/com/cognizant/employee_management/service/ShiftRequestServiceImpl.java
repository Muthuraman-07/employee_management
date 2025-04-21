package com.cognizant.employee_management.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.ShiftRequestDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.model.ShiftRequest;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.ShiftRepository;
import com.cognizant.employee_management.repository.ShiftRequestRepository;

@Service
public class ShiftRequestServiceImpl implements ShiftRequestService {

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
    public ShiftRequestDto requestShiftSwap(int employeeId, int shiftId) {
        log.info("[SHIFT-SERVICE] Requesting shift swap for employee ID: {} and shift ID: {}", employeeId, shiftId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        ShiftRequest shiftRequest = new ShiftRequest();
        shiftRequest.setEmployee(employee);
        shiftRequest.setRequestedShift(shift);
        shiftRequest.setStatus("PENDING");
        shiftRequest.setApprovedByManager(false);

        ShiftRequest savedRequest = shiftRequestRepository.save(shiftRequest);
        log.info("[SHIFT-SERVICE] Shift swap request created successfully with ID: {}", savedRequest.getId());
        return modelMapper.map(savedRequest, ShiftRequestDto.class);
    }

    @Override
    public ShiftRequestDto approveShiftSwap(int requestId, boolean approved) {
        log.info("[SHIFT-SERVICE] Approving shift swap request ID: {}", requestId);
        ShiftRequest shiftRequest = shiftRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Shift request not found"));

        if (approved) {
            shiftRequest.setStatus("Approved");
            shiftRequest.setApprovedByManager(true);
        } else {
            shiftRequest.setStatus("Rejected");
        }

        ShiftRequest updatedRequest = shiftRequestRepository.save(shiftRequest);
        log.info("[SHIFT-SERVICE] Shift swap request ID: {} updated successfully", requestId);
        return modelMapper.map(updatedRequest, ShiftRequestDto.class);
    }
}
