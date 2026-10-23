package com.acme.salarymanagement.repository;

import java.math.BigDecimal;

public interface SalarySummeryProjection {

	String getCurrency();
	
	Long getEmployeeCount();
	
	BigDecimal getTotalSalary();
	
	BigDecimal getAverageSalary();
	
	BigDecimal getHighestSalary();
	
	BigDecimal getLowestSalary();
	
}
