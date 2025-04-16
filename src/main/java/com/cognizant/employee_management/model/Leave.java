package com.cognizant.employee_management.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
@Data
@Entity
@Table(name="employee_leave")
public class Leave {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaveId;
	@ManyToOne
	@JoinColumn(name="employeeId")
    private Employee employee;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime appliedDate;
	@Column(length = 30)
	private String leaveType;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime endDate;
	@Column(length = 20)
	private String status;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime approvedDate;
}