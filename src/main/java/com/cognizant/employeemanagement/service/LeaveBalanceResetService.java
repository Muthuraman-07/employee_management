package com.cognizant.employeemanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cognizant.employeemanagement.model.Employee;
import com.cognizant.employeemanagement.model.LeaveBalance;
import com.cognizant.employeemanagement.repository.EmployeeRepository;
import com.cognizant.employeemanagement.repository.LeaveBalanceRepository;

@Service
public class LeaveBalanceResetService {

	private static final Logger log = LoggerFactory.getLogger(LeaveBalanceResetService.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;

	@Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
	public void resetLeaveBalances() {
		log.info("[LEAVE-BALANCE-RESET] Starting daily leave balance reset process");
		try {
			List<Employee> employees = employeeRepository.findAll();
			LocalDate today = LocalDate.now();

			for (Employee employee : employees) {
				LocalDate joinedDate = employee.getJoinedDate();
				if (today.isEqual(joinedDate.plusYears(1))) {
					log.info("[LEAVE-BALANCE-RESET] Employee eligible for leave balance reset: {}",
							employee.getEmployeeId());
					resetLeaveBalanceForEmployee(employee);
				}
			}

			log.info("[LEAVE-BALANCE-RESET] Leave balance reset process completed successfully");
		} catch (Exception e) {
			log.error("[LEAVE-BALANCE-RESET] Error during leave balance reset process: {}", e.getMessage(), e);
		}
	}

	private void resetLeaveBalanceForEmployee(Employee employee) {
		log.info("[LEAVE-BALANCE-RESET] Resetting leave balances for employee ID: {}", employee.getEmployeeId());
		try {
			List<LeaveBalance> leaveBalances = leaveBalanceRepository.findByEmployee(employee);

			for (LeaveBalance leaveBalance : leaveBalances) {
				switch (leaveBalance.getLeaveType()) {
				case "Vacation":
					leaveBalance.setBalance(10);
					break;
				case "Sick Leave":
					leaveBalance.setBalance(5);
					break;
				case "Casual Leave":
					leaveBalance.setBalance(7);
					break;
				default:
					log.warn("[LEAVE-BALANCE-RESET] Unknown leave type '{}' for employee ID: {}",
							leaveBalance.getLeaveType(), employee.getEmployeeId());
					continue;
				}
				leaveBalanceRepository.save(leaveBalance);
				log.info("[LEAVE-BALANCE-RESET] Leave balance for type '{}' reset to {} for employee ID: {}",
						leaveBalance.getLeaveType(), leaveBalance.getBalance(), employee.getEmployeeId());
			}
		} catch (Exception e) {
			log.error("[LEAVE-BALANCE-RESET] Error resetting leave balances for employee ID: {}. Error: {}",
					employee.getEmployeeId(), e.getMessage(), e);
		}
	}
}
