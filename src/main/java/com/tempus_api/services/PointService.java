package com.tempus_api.services;

import com.tempus_api.dtos.PointDto;
import com.tempus_api.models.Employee;
import com.tempus_api.models.Point;
import com.tempus_api.repositories.PointRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PointService {

    @Autowired
    PointRepository pointRepository;

    @Autowired
    EmployeeService employeeService;

    public Point createPoint(PointDto pointDto){
        Point point = new Point();
        BeanUtils.copyProperties(pointDto, point);
        Employee employee = employeeService.findById(pointDto.employeeId());
        point.setEmployee(employee);
        return pointRepository.save(point);
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
        return pointRepository.save(point);
    }


    public Point findById(UUID id){
        return pointRepository.findById(id).orElseThrow(() -> new RuntimeException("cannot be found"));
    }

    public List<Point> findAll() {
        return pointRepository.findAll();
    }

    public void deletePoint(UUID id){
        Point point = findById(id);
        pointRepository.delete(point);
    }
}
