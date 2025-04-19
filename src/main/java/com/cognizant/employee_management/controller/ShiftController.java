
package com.cognizant.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cognizant.employee_management.dto.ShiftDto;
import com.cognizant.employee_management.service.ShiftService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @PostMapping
    public ResponseEntity<ShiftDto> createShift(@Valid @RequestBody ShiftDto shiftDto) {
        ShiftDto createdShift = shiftService.createShift(shiftDto);
        return new ResponseEntity<>(createdShift, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftDto> getShiftById(@PathVariable int id) {
        ShiftDto shift = shiftService.getShiftById(id);
        return new ResponseEntity<>(shift, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ShiftDto>> getAllShifts() {
        List<ShiftDto> shifts = shiftService.getAllShifts();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftDto> updateShift(@PathVariable int id,@Valid @RequestBody ShiftDto shiftDto) {
        ShiftDto updatedShift = shiftService.updateShift(id, shiftDto);
        return new ResponseEntity<>(updatedShift, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShiftDto> patchShift(@PathVariable int id,@Valid @RequestBody ShiftDto shiftDto) {
        ShiftDto patchedShift = shiftService.patchShift(id, shiftDto);
        return new ResponseEntity<>(patchedShift, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShift(@PathVariable int id) {
        shiftService.deleteShift(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }
    
    
    
    
  
}
