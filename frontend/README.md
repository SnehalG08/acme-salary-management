
# ACME Salary Management System

## Project Overview
The ACME Salary Management System is a web application that helps HR managers manage employee records and salary information across different countries and departments.

## Technology Stack
- **Backend:** Java 17, Spring Boot 3.5.1, Spring Data JPA, Hibernate
- **Frontend:** Angular 18, TypeScript, HTML, CSS
- **Database:** MySQL
- **Testing:** JUnit, Spring Boot Test

## Features
- View employee records
- Add, edit, and delete employees
- Search employees by keyword
- Filter employees by country and department
- Sort and paginate employee records
- View salary summaries by currency
- Validate employee inputs and display API errors

## Project Structure
- `backend/` — Spring Boot REST API and database integration
- `frontend/` — Angular user interface

## Prerequisites
- Java 17
- Node.js and npm
- MySQL Server
- Angular CLI 18

## Run the Backend
1. Configure the MySQL connection in the backend application properties.
2. Open a terminal in the `backend` directory.
3. Start the application:

```bash
mvn spring-boot:run
```

If Maven is not installed globally and the project contains `mvnw.cmd`, run:

```bash
mvnw.cmd spring-boot:run
```

Backend URL: `http://localhost:8080`

## Run the Frontend
1. Open a terminal in the `frontend` directory.
2. Install dependencies:

```bash
npm install
```

3. Start the Angular development server:

```bash
npx ng serve
```

Frontend URL: `http://localhost:4200`

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/employees` | List employees with pagination and filters |
| GET | `/api/employees/{id}` | Get an employee by ID |
| POST | `/api/employees` | Add an employee |
| PUT | `/api/employees/{id}` | Update an employee |
| DELETE | `/api/employees/{id}` | Delete an employee |
| GET | `/api/employees/salary-summary` | Get salary summaries |

## Testing
Backend automated tests have passed during development. Employee management, search, filtering, pagination, and salary summaries have also been tested manually.

## Future Enhancements
- Authentication and role-based access
- Employee and salary report exports
- Additional frontend automated tests
