package com.cognizant.employee_management.dto;

import java.time.LocalDateTime;

import com.cognizant.employee_management.model.Employee;

public class ShiftRequestDto {

    private int id;
    private Employee employee;
    private int requestedShiftId;
    private String status;
    private boolean approvedByManager;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee Employee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getRequestedShiftId() {
        return requestedShiftId;
    }

    public void setRequestedShiftId(int requestedShiftId) {
        this.requestedShiftId = requestedShiftId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isApprovedByManager() {
        return approvedByManager;
    }

    public void setApprovedByManager(boolean approvedByManager) {
        this.approvedByManager = approvedByManager;
    }
}
