package com.tempus_api.controllers;


import com.tempus_api.dtos.EmployeeDto;
import com.tempus_api.models.Employee;
import com.tempus_api.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;


    @GetMapping("/all/{id}")
    public ResponseEntity<List<Employee>> findAll(@PathVariable(name = "id")UUID enterpriseId){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll(enterpriseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDto employeeDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                                   @PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.updateEmployee(employeeDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteMapping(@PathVariable(name = "id")UUID id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
