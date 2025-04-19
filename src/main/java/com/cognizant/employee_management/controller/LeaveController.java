package com.cognizant.employee_management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.cognizant.employee_management.dto.returnEmployeeDto;
import com.cognizant.employee_management.dto.returnLeaveDto;
import com.cognizant.employee_management.model.Leave;
import com.cognizant.employee_management.service.LeaveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {
	@Autowired
    private LeaveService leaveService;
 
//    @GetMapping("/allLeaves")
//    public List<LeaveDto> getAllLeaves() {
//        return leaveService.getAllLeaves();
//    }
	
	@GetMapping("/all")
//	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<List<LeaveDto>> getAllLeaves() {
		List<LeaveDto> leaves = leaveService.getAllLeaves();
//		System.out.println("Employees: " + employees);
		return ResponseEntity.ok(leaves);
	}
 
    @GetMapping("/leaveRequestStatus/{status}")
    public List<returnLeaveDto> getAllPendingLeaveRequests(@PathVariable String status) {
        return leaveService.getAllPendingLeaveRequests(status);
    }
    
    @GetMapping("/{id}")
    public LeaveDto getLeaveById(@PathVariable int id) {
        return leaveService.getLeaveById(id);
    }
 
    @PostMapping("/createLeave")
    public LeaveDto createLeave(@Valid @RequestBody LeaveDto dto) {
        return leaveService.createLeave(dto);
    }
 
    @PutMapping("update/{id}")
    public LeaveDto updateLeave(@PathVariable int id,@Valid @RequestBody LeaveDto dto) {
        return leaveService.updateLeave(id, dto);
    }
 
    @DeleteMapping("deleteLeave/{id}")
    public ResponseEntity<?> deleteLeave(@PathVariable int id) {
        leaveService.deleteLeave(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("applyLeave/{id}")
    public ResponseEntity<String> applyLeave(@PathVariable int id,@RequestBody LeaveDto leaveDto) {
    	leaveService.applyLeave(id,leaveDto);
        return ResponseEntity.ok("Leave Applied");
    }

}
