package com.cognizant.employee_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.LeaveBalanceDto;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService{
	
	@Autowired
    private LeaveBalanceRepository leaveBalanceRepository;
 
    @Autowired
    private ModelMapper modelMapper;
 
    @Override
    public LeaveBalanceDto createLeaveBalance(LeaveBalanceDto leaveBalanceDto) {
        LeaveBalance leaveBalance = modelMapper.map(leaveBalanceDto, LeaveBalance.class);
        LeaveBalance saved = leaveBalanceRepository.save(leaveBalance);
        return modelMapper.map(saved, LeaveBalanceDto.class);
    }
 
    @Override
    public LeaveBalanceDto getLeaveBalanceById(int id) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LeaveBalance not found"));
        return modelMapper.map(leaveBalance, LeaveBalanceDto.class);
    }
 
    @Override
    public List<LeaveBalanceDto> getAllLeaveBalances() {
        return leaveBalanceRepository.findAll().stream()
                .map(lb -> modelMapper.map(lb, LeaveBalanceDto.class))
                .collect(Collectors.toList());
    }
 
    @Override
    public LeaveBalanceDto updateLeaveBalance(int id, LeaveBalanceDto leaveBalanceDto) {
        LeaveBalance existing = leaveBalanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LeaveBalance not found"));
 
        existing.setEmployee(leaveBalanceDto.getEmployee());
        existing.setLeaveType(leaveBalanceDto.getLeaveType());
        existing.setBalance(leaveBalanceDto.getBalance());
 
        LeaveBalance updated = leaveBalanceRepository.save(existing);
        return modelMapper.map(updated, LeaveBalanceDto.class);
    }
 
 
    @Override
    public void deleteLeaveBalance(int id) {
        leaveBalanceRepository.deleteById(id);
    }

}
