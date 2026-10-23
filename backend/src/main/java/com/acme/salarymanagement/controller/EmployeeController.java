package com.acme.salarymanagement.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.acme.salarymanagement.entity.Employee;
import com.acme.salarymanagement.repository.SalarySummeryProjection;
import com.acme.salarymanagement.service.EmployeeService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Employee createEmployee(@Valid @RequestBody Employee employee) {

		return employeeService.createEmployee(employee);
	}

	@GetMapping
	public Page<Employee> getAllEmployees(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "lastName") String sortBy,
			@RequestParam(defaultValue = "asc") String direction, 
			@RequestParam(required = false) String country, 
			@RequestParam(required = false) String department, 
			@RequestParam(required = false) String search) {

		return employeeService.getEmployeesPaginated(page, size, sortBy, direction,country, 
				department, search);
	}

	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable Long id) {
		return employeeService.getEmployeeById(id);
	}

	@PutMapping("/{id}")
	public Employee updatEmployee(@PathVariable Long id, @Valid @RequestBody Employee updatEmployee) {
		return employeeService.updateEmployee(id, updatEmployee);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
	}
	
	@GetMapping("/salary-summary")
	public List<SalarySummeryProjection> getSalarySummery(){
		return employeeService.getSalarySummery();
		
	}
}
