package com.cognizant.employee_management.dto;

import java.time.LocalDateTime;

import com.cognizant.employee_management.model.Employee;

import lombok.Data;
@Data
public class AttendanceDto {
	private int attendanceID;
    private Employee employee;
	private LocalDateTime clockInTime;
	private LocalDateTime clockOutTime;
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
	private float workHours;
	private int isPresent;
}
