package com.employeeservice.employeeapplication.controller;

import com.employeeservice.employeeapplication.Model.Employee;
import com.employeeservice.employeeapplication.Repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/getemployees")
    public List<Employee> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(System.out::println);
        return employees;
    }

    @PostMapping("/addEmployee")
    public Employee addNewEmployee(@RequestBody Employee employee){
        System.out.println(employee);
        Employee e =employeeRepository.save(employee);
        return e;
    }

    @PutMapping("/updateEmployee")
    public Employee updateEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/deleteEmployee")
    public void deleteEmployee(@RequestBody Employee employee){
        employeeRepository.delete(employee);
        return;
    }
}
