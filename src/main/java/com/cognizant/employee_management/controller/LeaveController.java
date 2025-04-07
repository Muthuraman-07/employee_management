package com.cognizant.employee_management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.LeaveDto;
import com.cognizant.employee_management.service.LeaveService;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {
	@Autowired
    private LeaveService leaveService;
 
    @GetMapping("/all")
    public List<LeaveDto> getAllLeaves() {
        return leaveService.getAllLeaves();
    }
 
    @GetMapping("/{id}")
    public LeaveDto getLeaveById(@PathVariable int id) {
        return leaveService.getLeaveById(id);
    }
 
    @PostMapping
    public LeaveDto createLeave(@RequestBody LeaveDto dto) {
        return leaveService.createLeave(dto);
    }
 
    @PutMapping("/{id}")
    public LeaveDto updateLeave(@PathVariable int id, @RequestBody LeaveDto dto) {
        return leaveService.updateLeave(id, dto);
    }
 
    @PatchMapping("/{id}")
    public LeaveDto patchLeave(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        return leaveService.patchLeave(id, updates);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLeave(@PathVariable int id) {
        leaveService.deleteLeave(id);
        return ResponseEntity.ok().build();
    }

}
