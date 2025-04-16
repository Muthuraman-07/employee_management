package com.cognizant.employee_management.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Entity
@Data
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveBalanceID;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @Column(length = 20)
    private String leaveType;

    private int balance;

    public LeaveBalance(Employee employee, String leaveType, int balance) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.balance = balance;
    }

    // Default constructor
    public LeaveBalance() {}
}
