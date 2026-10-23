package com.acme.salarymanagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.acme.salarymanagement.entity.Employee;
import com.acme.salarymanagement.repository.EmployeeRepository;

@Component
public class EmployeeDataSeeder implements CommandLineRunner {

	private final EmployeeRepository employeeRepository;

	public EmployeeDataSeeder(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}


@Override
public void run(String... args) throws Exception {

    long existingCount = employeeRepository.count();

    if (existingCount >= 10000) {
        System.out.println(
            "Employee data already contains " + existingCount + " records."
        );
        return;
    }

    String[] firstNames = {
        "Arav", "Sneha", "Rahul", "Priya", "Amit",
        "Neha", "John", "Emma", "Abhi", "David"
    };

    String[] lastNames = {
        "Sharma", "Golam", "Deshmukh", "Verma", "Brown", "Smith"
    };

    String[] countries = {
        "India", "USA", "UK", "Germany", "Canada"
    };

    String[] departments = {
        "Engineering", "HR", "Finance", "Marketing", "Operations"
    };

    String[][] countryCurrency = {
        {"India", "INR"},
        {"USA", "USD"},
        {"UK", "GBP"},
        {"Germany", "EUR"},
        {"Canada", "CAD"}
    };

    Random random = new Random();
    List<Employee> employees = new ArrayList<>();

    int recordsToCreate = (int) (10000 - existingCount);
    int emailNumber = 1;

    while (employees.size() < recordsToCreate) {

        String email = "employee" + emailNumber + "@example.com";
        emailNumber++;

        // Skip email addresses that already exist in the database.
        if (employeeRepository.existsByEmail(email)) {
            continue;
        }

        Employee employee = new Employee();

        employee.setFirstName(
            firstNames[random.nextInt(firstNames.length)]
        );
        employee.setLastName(
            lastNames[random.nextInt(lastNames.length)]
        );
        employee.setEmail(email);

        int countryIndex = random.nextInt(countries.length);

        employee.setCountry(countries[countryIndex]);
        employee.setDepartment(
            departments[random.nextInt(departments.length)]
        );

        int salaryAmount = 30000 + random.nextInt(170001);
        employee.setSalary(BigDecimal.valueOf(salaryAmount));
        employee.setCurrency(countryCurrency[countryIndex][1]);

        employees.add(employee);
    }

    employeeRepository.saveAll(employees);

    System.out.println(
        "Successfully generated " + employees.size() + " employee records."
    );
}

		
}
