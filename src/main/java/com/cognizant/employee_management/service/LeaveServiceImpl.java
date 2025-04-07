package com.cognizant.employee_management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.model.Leave;
import com.cognizant.employee_management.repository.LeaveRepository;

@Service
public class LeaveServiceImpl implements LeaveService{
	@Autowired
    private LeaveRepository leaveRepository;
 
    @Autowired
    private ModelMapper modelMapper;
 
    @Override
    public List<LeaveDto> getAllLeaves() {
        return leaveRepository.findAll().stream()
                .map(leave -> modelMapper.map(leave, LeaveDto.class))
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
 
        leaveDto.setLeaveId(id); 
        Leave updatedLeave = modelMapper.map(leaveDto, Leave.class);
        Leave savedLeave = leaveRepository.save(updatedLeave);
        return modelMapper.map(savedLeave, LeaveDto.class);
    }
 
    @Override
    public LeaveDto patchLeave(int id, Map<String, Object> updates) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
 
        if (updates.containsKey("status"))
            leave.setStatus((String) updates.get("status"));
 
        if (updates.containsKey("approvedDate"))
            leave.setApprovedDate(LocalDateTime.parse((String) updates.get("approvedDate")));
 
        Leave updatedLeave = leaveRepository.save(leave);
        return modelMapper.map(updatedLeave, LeaveDto.class);
    }
 
    @Override
    public void deleteLeave(int id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
        leaveRepository.delete(leave);
    }
}
