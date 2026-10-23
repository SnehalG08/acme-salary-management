package com.acme.salarymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SalaryManagementApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void getEmployeeShouldReturnPeginatedResultes() throws Exception {

		mockMvc.perform(get("/api/employees").param("page", "0").param("size", "10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size").value(10)).andExpect(jsonPath("$.number").value(0))
				.andExpect(jsonPath("$.totalElements").value(10000))
				.andExpect(jsonPath("$.content.length()").value(10));

	}

	@Test
	void getEmployeeWithNegativePageShouldReturnBadRequest() throws Exception {

		mockMvc.perform(get("/api/employees").param("page", "-1").param("size", "10"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createEmployeeWithInvalidDataShouldReturnBadRequest() throws Exception {

		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/employees")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON).content("""
						{
						 "firstName": "",
						                      "lastName": "Sharma",
						                      "email": "invalid-email",
						                      "country": "India",
						                      "department": "Engineering",
						                      "salary": -100,
						                      "currency": "INR"
						}
						""")).andExpect(status().isBadRequest());
	}

	@Test
	void filterEmployeesByDepartmentShouldReturnOnlyMachingEmployees() throws Exception {

		mockMvc.perform(get("/api/employees").param("department", "Engineering").param("page", "0").param("size", "10"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content.length()").value(10))
				.andExpect(jsonPath("$.content[*].department")
						.value(org.hamcrest.Matchers.everyItem(org.hamcrest.Matchers.is("Engineering"))));
	}
	
	@Test
	void getSalarySummeryShouldSummeryData() throws Exception{
		mockMvc.perform(get("/api/employees/salary-summary"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$[0].currency").exists())
		.andExpect(jsonPath("$[0].employeeCount").exists())
		.andExpect(jsonPath("$[0].totalSalary").exists())
		.andExpect(jsonPath("$[0].averageSalary").exists());
	}
}
