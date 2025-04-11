package com.cognizant.employee_management.dto;

import lombok.Data;

@Data
public class ErrorResponse {
	private int errorCode;
	private String msg;

}
