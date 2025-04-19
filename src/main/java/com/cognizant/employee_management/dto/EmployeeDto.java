package com.cognizant.employee_management.dto;
 
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
 
@Data
public class EmployeeDto {
    @NotNull(message = "Employee ID cannot be null")
    @Min(value = 1, message = "Employee ID must be greater than zero")
    private int employeeId;
 
    @NotNull(message = "Manager ID cannot be null")
    @Min(value = 1, message = "Manager ID must be greater than zero")
    private int managerId;
 
    @NotNull(message = "Username cannot be null")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;
 
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    private String password;
 
    @NotNull(message = "First Name cannot be null")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    private String firstName;
 
    @NotNull(message = "Last Name cannot be null")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    private String lastName;
 
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
 
    @NotNull(message = "Phone Number cannot be null")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number should be 10 digits")
    private String phoneNumber;
 
    @NotNull(message = "Department cannot be null")
    @Size(min = 2, max = 50, message = "Department must be between 2 and 50 characters")
    private String department;
 
    @NotNull(message = "Role cannot be null")
    @Size(min = 2, max = 50, message = "Role must be between 2 and 50 characters")
    private String role;
 
//    @NotNull(message = "Shift cannot be null")
    private int shiftId;
 
    @NotNull(message = "Joined Date cannot be null")
    @Past(message = "Joined Date must be in the past")
    private LocalDate joinedDate;
 
}