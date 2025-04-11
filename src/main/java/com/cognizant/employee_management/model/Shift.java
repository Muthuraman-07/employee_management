package com.cognizant.employee_management.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Shift {
	@Id
	private int shiftId;

	@NotNull(message = "Shift date should not be null")
	private LocalDate shiftDate;

	@NotNull(message = "Shift start time should not be null")
	private LocalTime shiftStartTime;

	@NotNull(message = "Shift end time should not be null")
	private LocalTime shiftEndTime;

}
