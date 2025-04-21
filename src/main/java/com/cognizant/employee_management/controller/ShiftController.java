package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.employee_management.dto.ShiftDto;
import com.cognizant.employee_management.service.ShiftService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/shifts")
@Slf4j // Lombok annotation for logging
public class ShiftController {

	@Autowired
	private ShiftService shiftService;

	@PostMapping
	public ResponseEntity<ShiftDto> createShift(@Valid @RequestBody ShiftDto shiftDto) {
		log.info("[SHIFT-CONTROLLER] Creating new shift");
		try {
			ShiftDto createdShift = shiftService.createShift(shiftDto);
			log.info("[SHIFT-CONTROLLER] Successfully created shift with ID: {}", createdShift.getShiftId());
			return new ResponseEntity<>(createdShift, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("[SHIFT-CONTROLLER] Error creating shift: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShiftDto> getShiftById(@PathVariable int id) {
		log.info("[SHIFT-CONTROLLER] Fetching shift with ID: {}", id);
		try {
			ShiftDto shift = shiftService.getShiftById(id);
			log.info("[SHIFT-CONTROLLER] Successfully fetched shift with ID: {}", id);
			return new ResponseEntity<>(shift, HttpStatus.OK);
		} catch (Exception e) {
			log.error("[SHIFT-CONTROLLER] Error fetching shift with ID: {}. Error: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping
	public ResponseEntity<List<ShiftDto>> getAllShifts() {
		log.info("[SHIFT-CONTROLLER] Fetching all shifts");
		try {
			List<ShiftDto> shifts = shiftService.getAllShifts();
			log.info("[SHIFT-CONTROLLER] Successfully fetched {} shifts", shifts.size());
			return new ResponseEntity<>(shifts, HttpStatus.OK);
		} catch (Exception e) {
			log.error("[SHIFT-CONTROLLER] Error fetching all shifts: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ShiftDto> updateShift(@PathVariable int id, @Valid @RequestBody ShiftDto shiftDto) {
		log.info("[SHIFT-CONTROLLER] Updating shift with ID: {}", id);
		try {
			ShiftDto updatedShift = shiftService.updateShift(id, shiftDto);
			log.info("[SHIFT-CONTROLLER] Successfully updated shift with ID: {}", id);
			return new ResponseEntity<>(updatedShift, HttpStatus.OK);
		} catch (Exception e) {
			log.error("[SHIFT-CONTROLLER] Error updating shift with ID: {}. Error: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteShift(@PathVariable int id) {
		log.info("[SHIFT-CONTROLLER] Deleting shift with ID: {}", id);
		try {
			shiftService.deleteShift(id);
			log.info("[SHIFT-CONTROLLER] Successfully deleted shift with ID: {}", id);
			return ResponseEntity.ok("Shift deleted successfully.");
		} catch (Exception e) {
			log.error("[SHIFT-CONTROLLER] Error deleting shift with ID: {}. Error: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting shift: " + e.getMessage());
		}
	}
}
