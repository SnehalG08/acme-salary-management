package com.acme.salarymanagement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.acme.salarymanagement.entity.Employee;
import com.acme.salarymanagement.exception.EmployeeNotFoundException;
import com.acme.salarymanagement.repository.EmployeeRepository;
import com.acme.salarymanagement.repository.SalarySummeryProjection;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));
	}

	public Employee updateEmployee(Long id, Employee updateEmployee) {

		Employee exitingEmployee = getEmployeeById(id);

		exitingEmployee.setFirstName(updateEmployee.getFirstName());
		exitingEmployee.setLastName(updateEmployee.getLastName());
		exitingEmployee.setEmail(updateEmployee.getEmail());
		exitingEmployee.setDepartment(updateEmployee.getDepartment());
		exitingEmployee.setCountry(updateEmployee.getCountry());
		exitingEmployee.setSalary(updateEmployee.getSalary());
		exitingEmployee.setCurrency(updateEmployee.getCurrency());

		return employeeRepository.save(exitingEmployee);
	}

	public void deleteEmployee(Long id) {

		Employee employee = getEmployeeById(id);

		employeeRepository.delete(employee);
	}

	public Page<Employee> getEmployeesPaginated(int page, int size, String sortBy, String direction, String country,
			String department, String search) {

		if (page < 0) {
			throw new IllegalArgumentException("Page number can not be negative");
		}

		if (size < 1 || size > 100) {

			throw new IllegalArgumentException("Page size must be between 1 to 100");
		}
		if (!java.util.Set.of("id", "firstName", "lastName", "email", "country", "department", "salary", "currency")
				.contains(sortBy)) {

			throw new IllegalArgumentException("Invalid sort field:" + sortBy);
		}

		if (!"asc".equalsIgnoreCase(direction) && !"desc".equalsIgnoreCase(direction)) {

			throw new IllegalArgumentException("Sort direction must be asc or desc");
		}

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);
		
		if (search != null && !search.isBlank()) {
			
			
			if(country !=null && !country.isBlank() && department != null && !department.isBlank()) {
				return employeeRepository.searchEmployeesByCountryAndDepartment
						(search.trim(), country, department, pageable);
						
			}
			
			if(country != null && !country.isBlank()) {
				
				return employeeRepository.searchEmployeesByCountry(search.trim(), country , pageable);
			}
			
			if(department != null && !department.isBlank()) {
				
				return employeeRepository.searchEmployeesByDepartment(search.trim(), department, pageable);
			}
			return employeeRepository.searchEmployees(search.trim(), pageable);
		}

		if (country != null && !country.isBlank() && department != null && !department.isBlank()) {

			return employeeRepository.findByCountryIgnoreCaseAndDepartmentIgnoreCase(country, department, pageable);
		}

		if (country != null && !country.isBlank()) {
			return employeeRepository.findByCountryIgnoreCase(country, pageable);
		}
		
		if(department != null && !department.isBlank()) {
			return employeeRepository.findByDepartmentIgnoreCase(department, pageable);
		}

		return employeeRepository.findAll(pageable);
	}

	public List<SalarySummeryProjection> getSalarySummery() {
		return employeeRepository.getSalarySummery();

	}
}
