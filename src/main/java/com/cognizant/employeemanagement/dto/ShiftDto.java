package com.cognizant.employeemanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShiftDto {
	@NotNull(message = "Shift ID cannot be null")
	private int shiftId;

	@NotNull(message = "Shift Date cannot be null")
	private LocalDate shiftDate;

	@NotNull(message = "Shift Start Time cannot be null")
	private LocalTime shiftStartTime;

	@NotNull(message = "Shift End Time cannot be null")
	private LocalTime shiftEndTime;

}