package com.cognizant.employee_management.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
	private String username;
	private String password;
}
