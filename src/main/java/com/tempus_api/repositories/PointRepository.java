package com.tempus_api.repositories;

import com.tempus_api.models.Employee;
import com.tempus_api.models.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PointRepository extends JpaRepository<Point, UUID> {

    List<Point> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);


}
