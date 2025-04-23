package com.cognizant.employeemanagement.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Shift {
	public static Shift MORNING;
	@Id
	private int shiftId;
	private LocalDate shiftDate;
	private LocalTime shiftStartTime;
	private LocalTime shiftEndTime;
}
