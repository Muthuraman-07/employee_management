package com.cognizant.employee_management.exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message)
	{
		super(message);
	}

}
