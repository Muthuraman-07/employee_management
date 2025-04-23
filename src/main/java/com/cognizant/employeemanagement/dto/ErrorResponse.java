package com.cognizant.employeemanagement.dto;

import lombok.Data;

@Data
public class ErrorResponse {
	private int errorCode;
	private String msg;

}
