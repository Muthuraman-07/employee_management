package com.cognizant.employee_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.LeaveBalanceDto;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

	private static final Logger log = LoggerFactory.getLogger(LeaveBalanceServiceImpl.class);

	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public LeaveBalanceDto createLeaveBalance(LeaveBalanceDto leaveBalanceDto) {
		log.info("[LEAVE-BALANCE-SERVICE] Creating leave balance for employee ID: {}",
				leaveBalanceDto.getEmployee().getEmployeeId());
		try {
			LeaveBalance leaveBalance = modelMapper.map(leaveBalanceDto, LeaveBalance.class);
			LeaveBalance savedLeaveBalance = leaveBalanceRepository.save(leaveBalance);
			log.info("[LEAVE-BALANCE-SERVICE] Leave balance created successfully for employee ID: {}",
					leaveBalanceDto.getEmployee().getEmployeeId());
			return modelMapper.map(savedLeaveBalance, LeaveBalanceDto.class);
		} catch (Exception e) {
			log.error("[LEAVE-BALANCE-SERVICE] Error creating leave balance for employee ID: {}. Error: {}",
					leaveBalanceDto.getEmployee().getEmployeeId(), e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<LeaveBalanceDto> getAllLeaveBalances() {
		log.info("[LEAVE-BALANCE-SERVICE] Fetching all leave balances");
		try {
			List<LeaveBalance> leaveBalances = leaveBalanceRepository.findAll();
			log.info("[LEAVE-BALANCE-SERVICE] Successfully fetched {} leave balances", leaveBalances.size());
			return leaveBalances.stream().map(lb -> modelMapper.map(lb, LeaveBalanceDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("[LEAVE-BALANCE-SERVICE] Error fetching all leave balances. Error: {}", e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void deleteLeaveBalance(int id) {
		log.info("[LEAVE-BALANCE-SERVICE] Deleting leave balance with ID: {}", id);
		try {
			if (!leaveBalanceRepository.existsById(id)) {
				log.warn("[LEAVE-BALANCE-SERVICE] Leave balance not found with ID: {}", id);
				throw new RuntimeException("LeaveBalance not found with id: " + id);
			}
			leaveBalanceRepository.deleteById(id);
			log.info("[LEAVE-BALANCE-SERVICE] Leave balance deleted successfully with ID: {}", id);
		} catch (Exception e) {
			log.error("[LEAVE-BALANCE-SERVICE] Error deleting leave balance with ID: {}. Error: {}", id, e.getMessage(),
					e);
			throw e;
		}
	}
}
