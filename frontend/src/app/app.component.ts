import { Component, OnInit } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { CommonModule } from "@angular/common";
import { EmployeeService } from "./employee.service";
import { Employee } from "./models/employee";
import { FormsModule

 } from "@angular/forms";
import { count } from "rxjs";

@Component({

  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, FormsModule],
  templateUrl: './app.Component.html',
  styleUrl:'./app.component.css'
})

export class AppComponent implements OnInit{
   title = "frontend";

   employees: Employee[] = [];
   totalElements = 0;
   errorMessage = '';

   searchText = '';
selectedCountry = '';
selectedDepartment = '';
currentPage = 0;
pageSize = 10;
totalPages = 0;
salarySummery: any[] = [];
showAddForm = false;
showEditForm = false;
editingEmployeeId: number | null = null;

   constructor(private employeeService: EmployeeService){}

    ngOnInit(): void{
 this.loadEmployees();
 this.loadSalarySummery();
}


loadEmployees(): void {
   this.errorMessage = '';
    this.employeeService.getEmployees(
       this.currentPage,
       this.pageSize,
       this.searchText,
       this.selectedCountry,
       this.selectedDepartment
  ).subscribe({
    next: (response) => {
      this.employees = response.content;
      this.totalElements = response.totalElements;
      this.totalPages = response.totalPages;
    },
    error: (error) => {
      console.error('Error loading employees:', error);
      this.errorMessage =
        'Unable to load employees. Please check the backend.';
    }
  });
}
loadSalarySummery(): void{
  this.employeeService.getSalarySummery().subscribe({
    next: (response) => {
      this.salarySummery = response;
    },
    error: (error) => {
      console.error('Error laoding salary summery:',error);
    }
  })
}
saveEmployee(): void {
  this.errorMessage = '';

  this.employeeService.addEmployee(this.newEmployee).subscribe({
    next: () => {
      alert('Employee added successfully!');

      this.showAddForm = false;

      this.newEmployee = {
        firstName: '',
        lastName: '',
        email: '',
        country: '',
        department: '',
        salary: 0,
        currency: 'INR'
      };

      this.currentPage = 0;
      this.loadEmployees();
      this.loadSalarySummery();
    },
    error: (error) => {
      console.error('Error adding employee:', error);
      this.errorMessage =
        error.error?.message || 'Unable to add employee. Please check the entered details.';
    }
  });
}
cancelAddEmployee(): void {
  this.showAddForm = false;

  this.newEmployee = {
    firstName: '',
    lastName: '',
    email: '',
    country: '',
    department: '',
    salary: 0,
    currency: 'INR'
  };
}
searchEmployees(): void{
  this.currentPage = 0;
  this.loadEmployees();
}

previousPage(): void{

  if(this.currentPage > 0){
    this.currentPage--;
    this.loadEmployees();
  }
}

nextPage(): void{

  if((this.currentPage + 1) * this.pageSize < this.totalElements){
    this.currentPage++;
    this.loadEmployees();
  
  }
}

newEmployee = {
  firstName: '',
  lastName: '',
  email: '',
  country: '',
  department: '',
  salary: 0,
  currency: 'INR'
};


editEmployee(employee: Employee): void{

  this.editingEmployeeId = employee.id;

  this.newEmployee = {
    firstName: employee.firstName,
    lastName: employee.lastName,
    email: employee.email,
    country: employee.country,
    department:employee.department,
    salary: employee.salary,
    currency: employee.currency
  };
  this.showEditForm = true;
  this.showAddForm = false;
}

updateEmployee(): void{
  if(this.editingEmployeeId === null){
    return;
  }
  this.employeeService.updateEmployee(this.editingEmployeeId,
    {
    id: this.editingEmployeeId,
    ...this.newEmployee
  }
  ).subscribe({
    next: () => {
      alert('Employee updated succefully!');

      this.showEditForm = false;
      this.editingEmployeeId = null;
      
      this.newEmployee = {
        firstName:'',
        lastName:'',
        email:'',
        country:'',
        department:'',
        salary:0,
        currency:'INR'
  };
  this.loadEmployees();
  this.loadSalarySummery();
},
error:(error) =>{
  console.error("Error updating employees: ", error);
this.errorMessage = 
error.error?.message || 'Unbale to update employee.';
  }
});
}

deleteEmployee(employee: Employee): void{
  const confirmed = confirm(
    ` Are you shure you want to delete ${employee.firstName} ${employee.lastName}?`
  );
  if(!confirmed){
    return;
  }

  this.employeeService.deleteEmployee(employee.id).subscribe({
next: () => {
  alert('Employee delted successfully');
  this.loadEmployees();
  this.loadEmployees();
},
error: (error) => {
    
  console.error('Error deleting employee:',error);
  this.errorMessage = error.error?.message || 'Unable to delete employee.';
}
 });
}
}