package com.cognizant.employee_management.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Attendance {
	@Id
	private int attendanceID;
	@ManyToOne
	@JoinColumn(name="employeeId")
    private Employee employee;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime clockInTime;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime clockOutTime;
	private float workHours;
	private int isPresent;
}
