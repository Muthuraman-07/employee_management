package com.cognizant.employee_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.model.Employee;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.EmployeeRepository;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveBalanceResetService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void resetLeaveBalances() {
        List<Employee> employees = employeeRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Employee employee : employees) {
            LocalDate joinedDate = employee.getJoinedDate();
            if (today.isEqual(joinedDate.plusYears(1))) {
                resetLeaveBalanceForEmployee(employee);
            }
        }
    }

    private void resetLeaveBalanceForEmployee(Employee employee) {
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
            }
            leaveBalanceRepository.save(leaveBalance);
        }
    }
}



