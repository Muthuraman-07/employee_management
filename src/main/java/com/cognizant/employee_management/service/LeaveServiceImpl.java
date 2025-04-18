package com.cognizant.employee_management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.dto.returnEmployeeDto;
import com.cognizant.employee_management.dto.returnLeaveDto;
import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.Leave;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;
import com.cognizant.employee_management.repository.LeaveRepository;

import jakarta.transaction.Transactional;

@Service
public class LeaveServiceImpl implements LeaveService{
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
        return leaveRepository.findAll().stream()
                .map(leave -> modelMapper.map(leave, LeaveDto.class))
                .collect(Collectors.toList());
    }
 
    @Override
	public List<returnLeaveDto> getAllPendingLeaveRequests(String status) {
		// TODO Auto-generated method stub
		List<Leave> leaves=leaveRepository.findByStatus(status);
        return leaves.stream()
                .map(leave -> modelMapper.map(leave, returnLeaveDto.class))
                .collect(Collectors.toList());
	}
    
    @Override
    public LeaveDto getLeaveById(int id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
        return modelMapper.map(leave, LeaveDto.class);
    }
 
    @Override
    public LeaveDto createLeave(LeaveDto leaveDto) {
        Leave leave = modelMapper.map(leaveDto, Leave.class);
        Leave savedLeave = leaveRepository.save(leave);
        return modelMapper.map(savedLeave, LeaveDto.class);
    }
 
    @Override
    public LeaveDto updateLeave(int id, LeaveDto leaveDto) {
        Leave existingLeave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
 
        Leave updatedLeave = modelMapper.map(leaveDto, Leave.class);
        Leave savedLeave = leaveRepository.save(updatedLeave);
        return modelMapper.map(savedLeave, LeaveDto.class);
    }
 
    @Override
    public void deleteLeave(int id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
        leaveRepository.delete(leave);
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
    @Override
    @Transactional
    public void applyLeave(int id, LeaveDto leaveDto) {
        // Check if the employee exists
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found!!!"));

        Leave leave = modelMapper.map(leaveDto, Leave.class);
        leave.setEmployee(employee);
        leave.setStatus("Pending");
        leave.setAppliedDate(LocalDateTime.now());
        Leave savedLeave = leaveRepository.save(leave);

        // Check if the leave is approved
        if ("Approved".equals(savedLeave.getStatus())) {
            // Check if the leave is available
            if (isLeaveAvailable(employee, savedLeave)) {
                leave.setApprovedDate(LocalDateTime.now());
                leave.setStatus("Approved");
                leaveRepository.save(leave);
                updateLeaveBalance(employee, savedLeave);
            } else {
                throw new RuntimeException("Insufficient leave balance for leave type: " + savedLeave.getLeaveType());
            }
        } else if ("Rejected".equals(savedLeave.getStatus())) {
            leave.setApprovedDate(LocalDateTime.now());
            leave.setStatus("Rejected");
            leaveRepository.save(leave);
        }
    }

    private boolean isLeaveAvailable(Employee employee, Leave leave) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeAndLeaveType(employee, leave.getLeaveType());
        int leaveDays = (int) java.time.temporal.ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
        return leaveBalance.getBalance() >= leaveDays;
    }

    private void updateLeaveBalance(Employee employee, Leave leave) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeAndLeaveType(employee, leave.getLeaveType());
        int leaveDays = (int) java.time.temporal.ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate()) + 1;
        leaveBalance.setBalance(leaveBalance.getBalance() - leaveDays);
        leaveBalanceRepository.save(leaveBalance);
    }
}
