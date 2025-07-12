package com.tempus_api.services;

import com.tempus_api.dtos.PointDto;
import com.tempus_api.models.Employee;
import com.tempus_api.models.Enterprise;
import com.tempus_api.models.Point;
import com.tempus_api.models.User;
import com.tempus_api.repositories.EmployeeRepository;
import com.tempus_api.repositories.EnterpriseRepository;
import com.tempus_api.repositories.PointRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PointService {

    @Autowired
    PointRepository pointRepository;

    @Autowired
    EnterpriseRepository enterpriseRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    public Point createPoint(PointDto pointDto) {
        Point point = new Point();

        point.setStart(pointDto.start());
        point.setFinish(pointDto.finish());
        point.setDate(pointDto.date());

        long minutes = Duration.between(point.getStart(), point.getFinish()).toMinutes();
        BigDecimal totalHoras = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        point.setTotal(totalHoras);

        point.setPayed(false);

        Employee employee = employeeService.findById(pointDto.employeeId());
        point.setEmployee(employee);

        return pointRepository.save(point);
    }




    public BigDecimal employeeEarnings(List<Point> points) {
        BigDecimal totalMoney = BigDecimal.ZERO;

        for (Point point : points) {
            if (!point.isPayed()) {
                BigDecimal totalHours = point.getTotal(); // Ex: 8.5
                BigDecimal earningPerHour = point.getEmployee().getEarningHour();
                BigDecimal earnings = totalHours.multiply(earningPerHour);

                totalMoney = totalMoney.add(earnings);
                point.setPayed(true);
            }
        }

        return totalMoney.setScale(2, RoundingMode.HALF_UP); // Arredonda para 2 casas decimais
    }


    public List<Point> findByDate(LocalDate start, LocalDate finish, UUID id){
        Employee employee = employeeService.findById(id);
        return pointRepository.findByEmployeeAndDateBetween(employee,start, finish);

    }

    public Point updatePoint(PointDto pointDto, UUID id){
        Point point = findById(id);
        if(pointDto.start() != null){
            point.setStart(pointDto.start());
        }
        if(pointDto.finish() != null){
            point.setFinish(pointDto.finish());
        }
        if(pointDto.date() != null){
            point.setDate(pointDto.date());
        }
        if(pointDto.employeeId() != null){
            Employee employee = employeeService.findById(pointDto.employeeId());
            point.setEmployee(employee);
        }
        if(pointDto.start() != null && pointDto.finish() != null){
            long minutes = Duration.between(point.getStart(), point.getFinish()).toMinutes();
            BigDecimal totalHoras = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
            point.setTotal(totalHoras);
        }

        return pointRepository.save(point);
    }


    public Point findById(UUID id){
        return pointRepository.findById(id).orElseThrow(() -> new RuntimeException("cannot be found"));
    }

    public List<Point> findAll(UUID id) {
        Employee employee = employeeService.findById(id);
        return pointRepository.findByEmployee(employee);
    }

    public void deletePoint(UUID id){
        Point point = findById(id);
        pointRepository.delete(point);
    }
}
