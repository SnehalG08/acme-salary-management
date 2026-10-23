package com.acme.salarymanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acme.salarymanagement.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Page<Employee> findByCountryIgnoreCase(String country, Pageable pageable);

	Page<Employee> findByDepartmentIgnoreCase(String department, Pageable pageable);

	Page<Employee> findByCountryIgnoreCaseAndDepartmentIgnoreCase(String country, String department, Pageable pageable);

	@Query("""
			SELECT e FROM Employee e
			WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
			   OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
			OR LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%'))
				""")
	Page<Employee> searchEmployees(@Param("search") String search, Pageable pageable);

	@Query("""
			SELECT e.currency As currency,
			COUNT(e) As employeeCount,
			SUM(e.salary) As totalSalary,
			AVG(e.salary) As averageSalary,
			MAX(e.salary) As highestSalary,
			MIN(e.salary) As lowestSalary
			From Employee e
			GROUP BY e.currency
			""")
	List<SalarySummeryProjection> getSalarySummery();

	@Query("""
			SELECT e FROM Employee e
			WHERE (
			LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
			OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
			OR LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%'))
			)
			AND LOWER(e.country) = LOWER(:country)
			AND LOWER(e.department) = LOWER(:department)
			""")
	Page<Employee> searchEmployeesByCountryAndDepartment(
			@Param("search") String search, 
			@Param("country") String country, 
			@Param("department") String department,
			Pageable pageable);

	@Query("""
			SELECT e FROM Employee e
			WHERE(
			LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
			OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
			OR LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%'))
			)
			AND LOWER(e.country) = LOWER(:country)
			""")
	Page<Employee> searchEmployeesByCountry(
		@Param("search")	String search, 
		@Param("country") String country,
		Pageable pageable);

	
	@Query("""
			SELECT e FROM Employee e
			WHERE(
			LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
			OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
			OR LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%'))
			)
			AND LOWER(e.department) = LOWER(:department)
			""")
	Page<Employee> searchEmployeesByDepartment(
			@Param("search") String search, 
			@Param("department") String department, 
			Pageable pageable);

	boolean existsByEmail(String email);
}
