
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Employee } from "./models/employee";


@Injectable({
    providedIn:'root'
})

export class EmployeeService{
     private apiUrl = 'http://localhost:8080/api/employees';

     constructor(private http:HttpClient){}

        
getEmployees(
  page: number = 0,
  size: number = 10,
  search: string = '',
  country: string = '',
  department: string = ''
): Observable<any> {
  let url = `${this.apiUrl}?page=${page}&size=${size}`;

  if (search) {
    url += `&search=${encodeURIComponent(search)}`;
  }

  if (country) {
    url += `&country=${encodeURIComponent(country)}`;
  }

  if (department) {
    url += `&department=${encodeURIComponent(department)}`;
  }

  return this.http.get<any>(url);
}
getSalarySummery(): Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/salary-summary`);
}


addEmployee(employee: Omit<Employee, 'id'>): Observable<Employee> {
  return this.http.post<Employee>(this.apiUrl, employee);
}
updateEmployee(id: number, employee: Employee) {
  return this.http.put<Employee>(
    `${this.apiUrl}/${id}`,
    employee
  );
}
deleteEmployee(id: number): Observable<void>{
  return this.http.delete<void>(
    `${this.apiUrl}/${id}`
  );
}

        }
     
