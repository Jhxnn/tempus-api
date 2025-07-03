package com.tempus_api.services;

import com.tempus_api.dtos.PointDto;
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

    public Point createPoint(PointDto pointDto){
        Point point = new Point();
        BeanUtils.copyProperties(pointDto, point);

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
