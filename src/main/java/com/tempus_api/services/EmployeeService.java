package com.tempus_api.services;

import com.tempus_api.dtos.EmployeeDto;
import com.tempus_api.models.Employee;
import com.tempus_api.models.Enterprise;
import com.tempus_api.models.User;
import com.tempus_api.repositories.EmployeeRepository;
import com.tempus_api.repositories.EnterpriseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EnterpriseService enterpriseService;

    @Autowired
    EnterpriseRepository enterpriseRepository;

    public Employee createEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        Enterprise enterprise= enterpriseService.findById(employeeDto.enterpriseId());
        employee.setEnterprise(enterprise);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(EmployeeDto employeeDto, UUID id){
        Employee employee = findById(id);
        if(employeeDto.name() != null){
            employee.setName(employeeDto.name());
        }
        if(employeeDto.earningHour() != null){
            employee.setEarningHour(employeeDto.earningHour());
        }
        if(employeeDto.password() != null){
            employee.setPassword(employeeDto.password());
        }
        if(employeeDto.enterpriseId() != null){
            Enterprise enterprise= enterpriseService.findById(employeeDto.enterpriseId());
            employee.setEnterprise(enterprise);
        }

        return employeeRepository.save(employee);
    }
    public boolean loginEmployee(String password, UUID id){
        Employee employee = findById(id);
        return employee.getPassword().equals(password);
    }

    public Employee findById(UUID id){
        return employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("cannot be found"));
    }

    public List<Employee> findAll(UUID id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Enterprise enterprise = enterpriseService.findById(id);
        return employeeRepository.findByEnterprise(enterprise);
    }

    public void deleteEmployee(UUID id){
        Employee employee = findById(id);
        employeeRepository.delete(employee);
    }

}
