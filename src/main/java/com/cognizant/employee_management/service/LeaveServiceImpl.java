package com.cognizant.employee_management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.dto.returnLeaveDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.Leave;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.repository.LeaveRepository;

import jakarta.transaction.Transactional;

@Service
public class LeaveServiceImpl implements LeaveService {

    private static final Logger log = LoggerFactory.getLogger(LeaveServiceImpl.class);

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<LeaveDto> getAllLeaves() {
        log.info("[LEAVE-SERVICE] Fetching all leave records");
        try {
            List<Leave> leaves = leaveRepository.findAll();
            log.info("[LEAVE-SERVICE] Successfully fetched {} leave records", leaves.size());

            return leaves.stream().map(leave -> {
                LeaveDto leaveDto = new LeaveDto();
                leaveDto.setLeaveId(leave.getLeaveId());
                leaveDto.setEmployeeId(leave.getEmployee().getEmployeeId());
                leaveDto.setAppliedDate(leave.getAppliedDate());
                leaveDto.setStartDate(leave.getStartDate());
                leaveDto.setEndDate(leave.getEndDate());
                leaveDto.setStatus(leave.getStatus());
                leaveDto.setLeaveType(leave.getLeaveType());
                leaveDto.setApprovedDate(leave.getApprovedDate());
                log.debug("[LEAVE-SERVICE] Mapped LeaveDto: {}", leaveDto);
                return leaveDto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[LEAVE-SERVICE] Error fetching leave records. Error: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<returnLeaveDto> getAllPendingLeaveRequests(String status) {
        log.info("[LEAVE-SERVICE] Fetching pending leave requests with status: {}", status);
        try {
            List<Leave> leaves = leaveRepository.findByStatus(status);
            log.info("[LEAVE-SERVICE] Successfully fetched {} leave requests with status: {}", leaves.size(), status);

            return leaves.stream()
                .map(leave -> modelMapper.map(leave, returnLeaveDto.class))
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[LEAVE-SERVICE] Error fetching leave requests with status: {}. Error: {}", status, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public LeaveDto getLeaveById(int id) {
        log.info("[LEAVE-SERVICE] Fetching leave record with ID: {}", id);
        try {
            Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[LEAVE-SERVICE] Leave not found with ID: {}", id);
                    return new RuntimeException("Leave not found with ID: " + id);
                });
            log.info("[LEAVE-SERVICE] Successfully fetched leave record with ID: {}", id);
            return modelMapper.map(leave, LeaveDto.class);
        } catch (Exception e) {
            log.error("[LEAVE-SERVICE] Error fetching leave record with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public LeaveDto createLeave(LeaveDto leaveDto) {
        log.info("[LEAVE-SERVICE] Creating leave record for employee ID: {}", leaveDto.getEmployeeId());
        try {
            Leave leave = modelMapper.map(leaveDto, Leave.class);
            Leave savedLeave = leaveRepository.save(leave);
            log.info("[LEAVE-SERVICE] Leave record created successfully for employee ID: {}", leaveDto.getEmployeeId());
            return modelMapper.map(savedLeave, LeaveDto.class);
        } catch (Exception e) {
            log.error("[LEAVE-SERVICE] Error creating leave record for employee ID: {}. Error: {}", leaveDto.getEmployeeId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public LeaveDto updateLeave(int id, LeaveDto leaveDto) {
        log.info("[LEAVE-SERVICE] Updating leave record with ID: {}", id);
        try {
            Leave existingLeave = leaveRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[LEAVE-SERVICE] Leave not found with ID: {}", id);
                    return new RuntimeException("Leave not found with ID: " + id);
                });

            Leave updatedLeave = modelMapper.map(leaveDto, Leave.class);
            updatedLeave.setLeaveId(existingLeave.getLeaveId());

            Leave savedLeave = leaveRepository.save(updatedLeave);
            log.info("[LEAVE-SERVICE] Leave record with ID: {} updated successfully", id);
            return modelMapper.map(savedLeave, LeaveDto.class);
        } catch (Exception e) {
            log.error("[LEAVE-SERVICE] Error updating leave record with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteLeave(int id) {
        log.info("[LEAVE-SERVICE] Deleting leave record with ID: {}", id);
        try {
            Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[LEAVE-SERVICE] Leave not found with ID: {}", id);
                    return new RuntimeException("Leave not found with ID: " + id);
                });
            leaveRepository.delete(leave);
            log.info("[LEAVE-SERVICE] Leave record with ID: {} deleted successfully", id);
        } catch (Exception e) {
            log.error("[LEAVE-SERVICE] Error deleting leave record with ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }


//    @Override
//	public void applyLeave(int id, LeaveDto leaveDto) {
//		// Check if the employee exists
//		Optional<Employee> container = employeeRepository.findById(id);
//		if (!container.isPresent()) {
//			throw new RuntimeException("Employee Not Found!!!");
//		}
//		Employee employee = container.get();
//		Leave leave = modelMapper.map(leaveDto, Leave.class);
//		leave.setEmployee(employee);
//		leave.setStatus("Pending");
//		leave.setAppliedDate(LocalDateTime.now());
//		Leave savedLeave = leaveRepository.save(leave);
//
//		if ("Approved".equals(savedLeave.getStatus())) {
//			if (isLeaveAvailable(employee, savedLeave)) {
//				leave.setApprovedDate(LocalDateTime.now());
//				leaveRepository.save(leave);
//				updateLeaveBalance(employee, savedLeave);
//			} else {
//				throw new RuntimeException("Insufficient leave balance for leave type: " + savedLeave.getLeaveType());
//			}
//		}
//		if ("Rejected".equals(savedLeave.getStatus())) {
//			leave.setApprovedDate(LocalDateTime.now());
//			leaveRepository.save(leave);
//		}
//	}
//    @Override
//    @Transactional
//    public void applyLeave(int id, LeaveDto leaveDto) {
//        // Check if the employee exists
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Employee Not Found!!!"));
//
//        Leave leave = modelMapper.map(leaveDto, Leave.class);
//        leave.setEmployee(employee);
//        leave.setStatus("Pending");
//        leave.setAppliedDate(LocalDateTime.now());
//        Leave savedLeave = leaveRepository.save(leave);
//
//        if ("Approved".equals(savedLeave.getStatus())) {
//            if (isLeaveAvailable(employee, savedLeave)) {
//                leave.setApprovedDate(LocalDateTime.now());
//                leaveRepository.save(leave);
//                updateLeaveBalance(employee, savedLeave);
//            } else {
//                throw new RuntimeException("Insufficient leave balance for leave type: " + savedLeave.getLeaveType());
//            }
//        } else if ("Rejected".equals(savedLeave.getStatus())) {
//            leave.setApprovedDate(LocalDateTime.now());
//            leaveRepository.save(leave);
//        }
//    }


//	private boolean isLeaveAvailable(Employee employee, Leave leave) {
//		// Find the leave balance for the employee and leave type
//		LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeAndLeaveType(employee, leave.getLeaveType());
//		int leaveDays = (int) java.time.temporal.ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
//		return leaveBalance.getBalance() >= leaveDays;
//	}
//
//	private void updateLeaveBalance(Employee employee, Leave leave) {
//		LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeAndLeaveType(employee, leave.getLeaveType());
//		int leaveDays = (int) java.time.temporal.ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
//		leaveBalance.setBalance(leaveBalance.getBalance() - leaveDays);
//		leaveBalanceRepository.save(leaveBalance);
//	}
    
    
    
    
    
//    @Override
//    @Transactional
//    public void applyLeave(int id, LeaveDto leaveDto) {
//        // Check if the employee exists
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Employee Not Found!!!"));
//
//        Leave leave = modelMapper.map(leaveDto, Leave.class);
//        leave.setEmployee(employee);
//        leave.setStatus("Pending");
//        leave.setAppliedDate(LocalDateTime.now());
//        Leave savedLeave = leaveRepository.save(leave);
//
//        // Check if the leave is approved
//        if ("Approved".equals(savedLeave.getStatus())) {
//            // Check if the leave is available
//            if (isLeaveAvailable(employee, savedLeave)) {
//                leave.setApprovedDate(LocalDateTime.now());
//                leave.setStatus("Approved");
//                leaveRepository.save(leave);
//                updateLeaveBalance(employee, savedLeave);
//            } else {
//                throw new RuntimeException("Insufficient leave balance for leave type: " + savedLeave.getLeaveType());
//            }
//        } else if ("Rejected".equals(savedLeave.getStatus())) {
//            leave.setApprovedDate(LocalDateTime.now());
//            leave.setStatus("Rejected");
//            leaveRepository.save(leave);
//        }
//    }
    
    
    
    
    
    

    @Transactional
    public void applyLeave(int id, LeaveDto leaveDto) {
        Logger log = LoggerFactory.getLogger(LeaveServiceImpl.class);
        log.info("[LEAVE-SERVICE] Applying leave for employee ID: {}", id);
        try {
            // Check if the employee exists
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("[LEAVE-SERVICE] Employee not found with ID: {}", id);
                        return new RuntimeException("Employee Not Found!!!");
                    });

            log.info("[LEAVE-SERVICE] Employee found: ID = {}", id);

            // Create new Leave object and populate details
            Leave leave = new Leave();
            leave.setEmployee(employee);
            leave.setLeaveType(leaveDto.getLeaveType());
            leave.setStartDate(leaveDto.getStartDate());
            leave.setEndDate(leaveDto.getEndDate());
            leave.setStatus("Pending");
            leave.setAppliedDate(LocalDateTime.now());

            // Save the leave request
            Leave savedLeave = leaveRepository.save(leave);
            log.info("[LEAVE-SERVICE] Leave applied successfully for employee ID: {}, Leave ID: {}", id, savedLeave.getLeaveId());

            // Handle approved leave
            if ("Approved".equals(savedLeave.getStatus())) {
                log.info("[LEAVE-SERVICE] Leave is approved for employee ID: {}", id);
                if (isLeaveAvailable(employee, savedLeave)) {
                    Leave latestLeave = leaveRepository.findById(savedLeave.getLeaveId())
                            .orElseThrow(() -> {
                                log.warn("[LEAVE-SERVICE] Leave not found with ID: {}", savedLeave.getLeaveId());
                                return new RuntimeException("Leave Not Found!!!");
                            });

                    latestLeave.setApprovedDate(LocalDateTime.now());
                    latestLeave.setStatus("Approved");
                    leaveRepository.save(latestLeave);

                    updateLeaveBalance(employee, latestLeave);
                    log.info("[LEAVE-SERVICE] Leave balance updated successfully for employee ID: {}", id);
                } else {
                    log.error("[LEAVE-SERVICE] Insufficient leave balance for employee ID: {}, Leave Type: {}", id, savedLeave.getLeaveType());
                    throw new RuntimeException("Insufficient leave balance for leave type: " + savedLeave.getLeaveType());
                }
            } else if ("Rejected".equals(savedLeave.getStatus())) {
                log.info("[LEAVE-SERVICE] Leave is rejected for employee ID: {}", id);
                Leave latestLeave = leaveRepository.findById(savedLeave.getLeaveId())
                        .orElseThrow(() -> {
                            log.warn("[LEAVE-SERVICE] Leave not found with ID: {}", savedLeave.getLeaveId());
                            return new RuntimeException("Leave Not Found!!!");
                        });

                latestLeave.setApprovedDate(LocalDateTime.now());
                latestLeave.setStatus("Rejected");
                leaveRepository.save(latestLeave);
                log.info("[LEAVE-SERVICE] Leave rejection status updated for employee ID: {}", id);
            }
        } catch (Exception e) {
            log.error("[LEAVE-SERVICE] Error applying leave for employee ID: {}. Error: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    private boolean isLeaveAvailable(Employee employee, Leave leave) {
        Logger log = LoggerFactory.getLogger(LeaveServiceImpl.class);
        log.info("[LEAVE-SERVICE] Checking leave availability for employee ID: {}, Leave Type: {}", employee.getEmployeeId(), leave.getLeaveType());
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeAndLeaveType(employee, leave.getLeaveType());

        if (leaveBalance == null) {
            log.warn("[LEAVE-SERVICE] No leave balance found for employee ID: {} and leave type: {}", employee.getEmployeeId(), leave.getLeaveType());
            return false;
        }

        int leaveDays = (int) java.time.temporal.ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
        boolean isAvailable = leaveBalance.getBalance() >= leaveDays;
        log.info("[LEAVE-SERVICE] Leave availability for employee ID: {}, Leave Type: {}: {}", employee.getEmployeeId(), leave.getLeaveType(), isAvailable);
        return isAvailable;
    }

    private void updateLeaveBalance(Employee employee, Leave leave) {
        Logger log = LoggerFactory.getLogger(LeaveServiceImpl.class);
        log.info("[LEAVE-SERVICE] Updating leave balance for employee ID: {}, Leave Type: {}", employee.getEmployeeId(), leave.getLeaveType());
        try {
            LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeAndLeaveType(employee, leave.getLeaveType());
            int leaveDays = (int) java.time.temporal.ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;

            leaveBalance.setBalance(leaveBalance.getBalance() - leaveDays);
            leaveBalanceRepository.save(leaveBalance);

            log.info("[LEAVE-SERVICE] Leave balance updated successfully for employee ID: {}, Remaining Balance: {}", employee.getEmployeeId(), leaveBalance.getBalance());
        } catch (Exception e) {
            log.error("[LEAVE-SERVICE] Error updating leave balance for employee ID: {}. Error: {}", employee.getEmployeeId(), e.getMessage(), e);
            throw e;
        }
    }
}

