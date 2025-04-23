package com.cognizant.employeemanagement.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int attendanceID;
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "employeeId")
	private Employee employee;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime clockInTime;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime clockOutTime;
	private float workHours;
	private int isPresent;
}
