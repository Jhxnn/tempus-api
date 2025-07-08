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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    public Point createPoint(PointDto pointDto){
        Point point = new Point();
        BeanUtils.copyProperties(pointDto, point);
        Employee employee = employeeService.findById(pointDto.employeeId());
        Duration duration = Duration.between(point.getStart(), point.getFinish());
        point.setTotal(duration.toHours());
        point.setPayed(false);
        point.setEmployee(employee);
        return pointRepository.save(point);
    }


    public double employeeEarnings(List<Point> points){

        double totalMoney = 0;
        for(Point point : points){
            totalMoney += point.getTotal() * point.getEmployee().getEarningHour();
            point.setPayed(true);
        }
        return totalMoney;
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
            Duration duration = Duration.between(pointDto.start(), pointDto.finish());
            point.setTotal(duration.toHours());
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
