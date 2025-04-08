package com.cognizant.employee_management.model;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int attendanceID;
	@ManyToOne
	@JoinColumn(name="employeeId")
    private Employee employee;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime clockInTime;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime clockOutTime;
	private float workHours;
	public int getAttendanceID() {
		return attendanceID;
	}
	public void setAttendanceID(int attendanceID) {
		this.attendanceID = attendanceID;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public LocalDateTime getClockInTime() {
		return clockInTime;
	}
	public void setClockInTime(LocalDateTime clockInTime) {
		this.clockInTime = clockInTime;
	}
	public LocalDateTime getClockOutTime() {
		return clockOutTime;
	}
	public void setClockOutTime(LocalDateTime clockOutTime) {
		this.clockOutTime = clockOutTime;
	}
	public float getWorkHours() {
		return workHours;
	}
	public void setWorkHours(float workHours) {
		this.workHours = workHours;
	}
	public int getIsPresent() {
		return isPresent;
	}
	public void setIsPresent(int isPresent) {
		this.isPresent = isPresent;
	}
	private int isPresent;
}
