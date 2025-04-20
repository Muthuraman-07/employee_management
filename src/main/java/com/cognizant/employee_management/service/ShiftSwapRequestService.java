package com.cognizant.employee_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import com.cognizant.employee_management.dto.EmployeeDto;
import com.cognizant.employee_management.dto.ShiftSwapRequestDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.ShiftSwapRequest;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.ShiftSwapRequestRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftSwapRequestService {

    private static final Logger log = LoggerFactory.getLogger(ShiftSwapRequestService.class);

    @Autowired
    private ShiftSwapRequestRepository shiftSwapRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ShiftSwapRequestDto createShiftSwapRequest(ShiftSwapRequestDto requestDto) {
        log.info("[SHIFT-SWAP-SERVICE] Creating shift swap request for requester ID: {} and approver ID: {}", 
                requestDto.getRequesterId(), requestDto.getApproverId());
        try {
            ShiftSwapRequest swapRequest = modelMapper.map(requestDto, ShiftSwapRequest.class);
            swapRequest.setRequestDate(LocalDate.now());
            swapRequest.setStatus("Pending");

            ShiftSwapRequest savedRequest = shiftSwapRequestRepository.save(swapRequest);
            log.info("[SHIFT-SWAP-SERVICE] Swap request created successfully with ID: {}", savedRequest.getRequestId());
            return modelMapper.map(savedRequest, ShiftSwapRequestDto.class);
        } catch (Exception e) {
            log.error("[SHIFT-SWAP-SERVICE] Error creating swap request for requester ID: {}. Error: {}", 
                    requestDto.getRequesterId(), e.getMessage(), e);
            throw new RuntimeException("Error creating shift swap request", e);
        }
    }

    public ShiftSwapRequestDto approveSwapRequestByEmployee(int requestId) {
        log.info("[SHIFT-SWAP-SERVICE] Approving swap request by employee for request ID: {}", requestId);
        try {
            ShiftSwapRequest request = shiftSwapRequestRepository.findById(requestId)
                    .orElseThrow(() -> {
                        log.warn("[SHIFT-SWAP-SERVICE] Swap request not found for ID: {}", requestId);
                        return new RuntimeException("Swap request not found");
                    });

            if (!request.getStatus().equals("Pending")) {
                log.warn("[SHIFT-SWAP-SERVICE] Swap request with ID: {} is not in 'Pending' status", requestId);
                throw new RuntimeException("Only 'Pending' requests can be approved");
            }

            request.setStatus("Submitted to Manager");
            ShiftSwapRequest updatedRequest = shiftSwapRequestRepository.save(request);
            log.info("[SHIFT-SWAP-SERVICE] Swap request with ID: {} approved by employee successfully", requestId);
            return modelMapper.map(updatedRequest, ShiftSwapRequestDto.class);
        } catch (Exception e) {
            log.error("[SHIFT-SWAP-SERVICE] Error approving swap request by employee for request ID: {}. Error: {}", requestId, e.getMessage(), e);
            throw new RuntimeException("Error approving swap request by employee", e);
        }
    }

    public ShiftSwapRequestDto approveSwapRequestByManager(int requestId) {
        log.info("[SHIFT-SWAP-SERVICE] Approving swap request by manager for request ID: {}", requestId);
        try {
            ShiftSwapRequest request = shiftSwapRequestRepository.findById(requestId)
                    .orElseThrow(() -> {
                        log.warn("[SHIFT-SWAP-SERVICE] Swap request not found for ID: {}", requestId);
                        return new RuntimeException("Swap request not found");
                    });

            if (!request.getStatus().equals("Submitted to Manager")) {
                log.warn("[SHIFT-SWAP-SERVICE] Swap request with ID: {} is not in 'Submitted to Manager' status", requestId);
                throw new RuntimeException("Only 'Submitted to Manager' requests can be approved");
            }

            Employee requesterEntity = employeeRepository.findById(request.getRequesterId())
                    .orElseThrow(() -> {
                        log.warn("[SHIFT-SWAP-SERVICE] Requester not found with ID: {}", request.getRequesterId());
                        return new RuntimeException("Requester not found");
                    });
            Employee approverEntity = employeeRepository.findById(request.getApproverId())
                    .orElseThrow(() -> {
                        log.warn("[SHIFT-SWAP-SERVICE] Approver not found with ID: {}", request.getApproverId());
                        return new RuntimeException("Approver not found");
                    });

            EmployeeDto requesterDto = modelMapper.map(requesterEntity, EmployeeDto.class);
            EmployeeDto approverDto = modelMapper.map(approverEntity, EmployeeDto.class);

            int tempShiftId = requesterDto.getShiftId();
            requesterDto.setShiftId(approverDto.getShiftId());
            approverDto.setShiftId(tempShiftId);

            requesterEntity = modelMapper.map(requesterDto, Employee.class);
            approverEntity = modelMapper.map(approverDto, Employee.class);
            employeeRepository.save(requesterEntity);
            employeeRepository.save(approverEntity);

            request.setStatus("Approved");
            ShiftSwapRequest updatedRequest = shiftSwapRequestRepository.save(request);
            log.info("[SHIFT-SWAP-SERVICE] Swap request with ID: {} approved by manager successfully", requestId);
            return modelMapper.map(updatedRequest, ShiftSwapRequestDto.class);
        } catch (Exception e) {
            log.error("[SHIFT-SWAP-SERVICE] Error approving swap request by manager for request ID: {}. Error: {}", requestId, e.getMessage(), e);
            throw new RuntimeException("Error approving swap request by manager", e);
        }
    }

    public List<ShiftSwapRequestDto> getRequestsByStatus(String status) {
        log.info("[SHIFT-SWAP-SERVICE] Fetching swap requests with status: {}", status);
        try {
            List<ShiftSwapRequestDto> requests = shiftSwapRequestRepository.findByStatus(status)
                    .stream()
                    .map(request -> modelMapper.map(request, ShiftSwapRequestDto.class))
                    .collect(Collectors.toList());
            log.info("[SHIFT-SWAP-SERVICE] Successfully fetched {} swap requests with status: {}", requests.size(), status);
            return requests;
        } catch (Exception e) {
            log.error("[SHIFT-SWAP-SERVICE] Error fetching swap requests with status: {}. Error: {}", status, e.getMessage(), e);
            throw new RuntimeException("Error fetching swap requests by status", e);
        }
    }
}
