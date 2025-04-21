package com.cognizant.employee_management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
	public List<returnLeaveDto> getAllLeaves() {
		log.info("[LEAVE-SERVICE] Fetching all leave records");
		try {
			List<Leave> leaves = leaveRepository.findAll();
			log.info("[LEAVE-SERVICE] Successfully fetched {} leave records", leaves.size());

			return leaves.stream().map(leave -> {
				returnLeaveDto leaveDto = new returnLeaveDto();
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

			return leaves.stream().map(leave -> modelMapper.map(leave, returnLeaveDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("[LEAVE-SERVICE] Error fetching leave requests with status: {}. Error: {}", status,
					e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void deleteLeave(int id) {
		log.info("[LEAVE-SERVICE] Deleting leave record with ID: {}", id);
		try {
			Leave leave = leaveRepository.findById(id).orElseThrow(() -> {
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

	@Transactional
	@Override
	public void applyLeave(int id, LeaveDto leaveDto) {
		// Check if the employee exists
		Logger log = LoggerFactory.getLogger(LeaveServiceImpl.class);
		log.info("[LEAVE-SERVICE] Applying leave for employee ID: {}", id);
		try {
			Optional<Employee> container = employeeRepository.findById(id);
			if (!container.isPresent()) {
				log.warn("[LEAVE-SERVICE] Employee not found with ID: {}", id);
				throw new RuntimeException("Employee Not Found!!!");
			}
			log.info("[LEAVE-SERVICE] Employee found: ID = {}", id);
			Employee employee = container.get();
			Leave leave = modelMapper.map(leaveDto, Leave.class);
			leave.setEmployee(employee);
			leave.setStatus("Approved");
			leave.setAppliedDate(LocalDateTime.now());
			Leave savedLeave = leaveRepository.save(leave);
			log.info("[LEAVE-SERVICE] Leave applied successfully for employee ID: {}, Leave ID: {}", id,
					savedLeave.getLeaveId());

			if (isLeaveAvailable(employee, savedLeave)) {
				if ("Approved".equals(savedLeave.getStatus())) {
					leave.setStatus("Approved");
					log.info("[LEAVE-SERVICE] Leave is approved for employee ID: {}", id);
					updateLeaveBalance(employee, savedLeave);
					log.info("[LEAVE-SERVICE] Leave balance updated successfully for employee ID: {}", id);
				} else {
					leave.setStatus("Rejected");
					log.info("[LEAVE-SERVICE] Leave rejection status updated for employee ID: {}", id);
				}
			} else {
				leave.setStatus("LOP");
				log.error("[LEAVE-SERVICE] Insufficient leave balance for employee ID: {}, Leave Type: {}", id,
						savedLeave.getLeaveType());
				throw new RuntimeException("Insufficient leave balance for leave type: " + savedLeave.getLeaveType());
			}
			leave.setApprovedDate(LocalDateTime.now());
			leaveRepository.save(leave);
		} catch (Exception e) {
			log.error("[LEAVE-SERVICE] Error applying leave for employee ID: {}. Error: {}", id, e.getMessage(), e);
			throw e;
		}
	}

	private boolean isLeaveAvailable(Employee employee, Leave leave) {
		// Find the leave balance for the employee and leave type
		Logger log = LoggerFactory.getLogger(LeaveServiceImpl.class);
		log.info("[LEAVE-SERVICE] Checking leave availability for employee ID: {}, Leave Type: {}",
				employee.getEmployeeId(), leave.getLeaveType());

		LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeAndLeaveType(employee, leave.getLeaveType());
		int leaveDays = (int) java.time.temporal.ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
		return leaveBalance.getBalance() >= leaveDays;
	}

	private void updateLeaveBalance(Employee employee, Leave leave) {
		Logger log = LoggerFactory.getLogger(LeaveServiceImpl.class);
		log.info("[LEAVE-SERVICE] Updating leave balance for employee ID: {}, Leave Type: {}", employee.getEmployeeId(),
				leave.getLeaveType());

		LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeAndLeaveType(employee, leave.getLeaveType());
		int leaveDays = (int) java.time.temporal.ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
		leaveBalance.setBalance(leaveBalance.getBalance() - leaveDays);
		leaveBalanceRepository.save(leaveBalance);
	}
}
