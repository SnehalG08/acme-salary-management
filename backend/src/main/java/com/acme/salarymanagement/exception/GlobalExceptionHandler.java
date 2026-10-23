package com.acme.salarymanagement.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleEmployeeNotFound(EmployeeNotFoundException exception) {

		Map<String, String> errorRespons = Map.of("error", "Not Found", 
				"message", exception.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRespons);

	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handlIllegalArgument(IllegalArgumentException exception) {

		Map<String, String> errorResponse = Map.of("error", "Bad Request",
				"message", exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

}
