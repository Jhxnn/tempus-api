package com.tempus_api.repositories;

import com.tempus_api.models.Employee;
import com.tempus_api.models.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    List<Employee> findByEnterprise(Enterprise enterprise);
}
