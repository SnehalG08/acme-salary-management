package com.acme.salarymanagement.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String firstName;
	
	@NotBlank
	@Column(nullable = false)
	private String lastName;
	
	@Email
	@NotBlank
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank
	@Column(nullable = false)
	private String country;
	
	@NotBlank
	@Column(nullable = false)
	private String department;
	
	@NotNull
	@PositiveOrZero
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal salary;
	
	@NotBlank
	@Column(nullable = false, length = 3)
	private String currency;
	
}
