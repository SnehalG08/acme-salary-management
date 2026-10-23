# ACME Salary Management System — Requirements

## Objective
Build a web application that helps ACME's HR Manager manage salary and employee information for approximately 10,000 employees across multiple countries.

## Primary User
**HR Manager:** Needs a central place to view, search, filter, and maintain employee records and review salary summaries.

## Functional Requirements
1. Display employee records with pagination and sorting.
2. Search employees using a keyword.
3. Filter employees by country and department.
4. Add a new employee with validated details.
5. Edit existing employee information.
6. Delete an employee after confirmation.
7. Display salary summaries, including employee count, total, average, highest, and lowest salary, grouped by currency.
8. Show meaningful validation and API error messages.

## Employee Data
Each employee record includes an ID, first name, last name, email, country, department, salary, and currency. Email addresses must be unique.

## Technical Requirements
- **Frontend:** Angular 18, TypeScript, HTML, CSS
- **Backend:** Java 17, Spring Boot, REST APIs, Spring Data JPA/Hibernate
- **Database:** MySQL
- **Testing:** Automated backend tests and manual frontend/API verification

## Acceptance Criteria
- HR can view, add, update, search, filter, sort, and delete employee records.
- Pagination works without loading all employee records at once.
- Salary summaries are returned by the backend and displayed in the UI.
- Invalid input and API errors are handled clearly.
- The application builds successfully, and backend automated tests pass.

## Scope Notes
The current implementation focuses on employee management and salary summaries. Authentication, role-based access, and report exports are future enhancements.