package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.LeaveBalanceDto;
import com.cognizant.employee_management.service.LeaveBalanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/leavebalance")
public class LeaveBalanceController {

	@Autowired
	private LeaveBalanceService leaveBalanceService;

	@PostMapping
	public LeaveBalanceDto create(@Valid @RequestBody LeaveBalanceDto dto) {
		return leaveBalanceService.createLeaveBalance(dto);
	}

	@GetMapping("/{id}")
	public LeaveBalanceDto getById(@PathVariable int id) {
		return leaveBalanceService.getLeaveBalanceById(id);
	}

	@GetMapping("/getAll")
	public List<LeaveBalanceDto> getAll() {
		return leaveBalanceService.getAllLeaveBalances();
	}

	@PutMapping("/{id}")
	public LeaveBalanceDto update(@PathVariable int id, @Valid @RequestBody LeaveBalanceDto dto) {
		return leaveBalanceService.updateLeaveBalance(id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		leaveBalanceService.deleteLeaveBalance(id);
		return ResponseEntity.ok("LeaveBalance deleted successfully.");
	}
}
