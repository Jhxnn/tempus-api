package com.tempus_api.controllers;

import com.tempus_api.dtos.PointDto;
import com.tempus_api.models.Point;
import com.tempus_api.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/point")
public class PointController {

    @Autowired
    PointService pointService;

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<Point>> findAll(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(pointService.findAll(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Point> findById(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(pointService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Point> createPoint(@RequestBody PointDto pointDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(pointService.createPoint(pointDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Point> updatePoint(@RequestBody PointDto pointDto,
                                             @PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.CREATED).body(pointService.updatePoint(pointDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Point> deletePoint(@PathVariable(name = "id") UUID id){
        pointService.deletePoint(id);
        return ResponseEntity.noContent().build();
    }

}
