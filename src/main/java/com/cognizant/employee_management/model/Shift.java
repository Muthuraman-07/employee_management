package com.cognizant.employee_management.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Shift {
	@Id
    private int shiftId;
    private LocalDate shiftDate;
    private LocalTime shiftStartTime;
    private LocalTime shiftEndTime;
	public int getShiftId() {
		return shiftId;
	}
	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}
	public LocalDate getShiftDate() {
		return shiftDate;
	}
	public void setShiftDate(LocalDate shiftDate) {
		this.shiftDate = shiftDate;
	}
	public LocalTime getShiftStartTime() {
		return shiftStartTime;
	}
	public void setShiftStartTime(LocalTime shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}
	public LocalTime getShiftEndTime() {
		return shiftEndTime;
	}
	public void setShiftEndTime(LocalTime shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}
    
    
    
}