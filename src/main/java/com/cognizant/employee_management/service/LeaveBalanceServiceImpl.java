package com.cognizant.employee_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.employee_management.dto.LeaveBalanceDto;
import com.cognizant.employee_management.model.LeaveBalance;
import com.cognizant.employee_management.repository.LeaveBalanceRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;
    @Override
    @Transactional
    public LeaveBalanceDto createLeaveBalance(@Valid @NotNull LeaveBalanceDto leaveBalanceDto) {
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
    @Transactional
    public LeaveBalanceDto updateLeaveBalance(int id, @Valid @NotNull LeaveBalanceDto leaveBalanceDto) {
        LeaveBalance existing = leaveBalanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LeaveBalance not found"));

        existing.setEmployee(leaveBalanceDto.getEmployee());
        existing.setLeaveType(leaveBalanceDto.getLeaveType());
        existing.setBalance(leaveBalanceDto.getBalance());

        LeaveBalance updated = leaveBalanceRepository.save(existing);
        return modelMapper.map(updated, LeaveBalanceDto.class);
    }


//    @Override
//    @Transactional
//    public LeaveBalanceDto patchLeaveBalance(int id,@NotNull LeaveBalanceDto leaveBalanceDto) {
//        LeaveBalance existing = leaveBalanceRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("LeaveBalance not found"));
//
//        if (leaveBalanceDto.getEmployee() != null) {
//            existing.setEmployee(leaveBalanceDto.getEmployee());
//        }
//        if (leaveBalanceDto.getLeaveType() != null) {
//            existing.setLeaveType(leaveBalanceDto.getLeaveType());
//        }
//        if (leaveBalanceDto.getBalance() != 0) {
//            existing.setBalance(leaveBalanceDto.getBalance());
//        }
//
//        LeaveBalance patched = leaveBalanceRepository.save(existing);
//        return modelMapper.map(patched, LeaveBalanceDto.class);
//    }


    @Override
    @Transactional
    public void deleteLeaveBalance(int id) {
        if (!leaveBalanceRepository.existsById(id)) {
            throw new RuntimeException("LeaveBalance not found");
        }
        leaveBalanceRepository.deleteById(id);
    }
}
