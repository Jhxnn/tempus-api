package com.tempus_api.controllers;

import com.tempus_api.dtos.EnterpriseDto;
import com.tempus_api.models.Enterprise;
import com.tempus_api.services.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enterprise")
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;

    @GetMapping
    public ResponseEntity<List<Enterprise>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(enterpriseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enterprise> findById(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(enterpriseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Enterprise> createEnterprise(@RequestBody EnterpriseDto enterpriseDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(enterpriseService.createEnterprise(enterpriseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enterprise> updateEnterprise(@RequestBody EnterpriseDto enterpriseDto,
                                                       @PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.CREATED).body(enterpriseService.updateEnterprise(enterpriseDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Enterprise> deleteEnterprise(@PathVariable(name = "id") UUID id){
        enterpriseService.deleteEnterprise(id);
        return ResponseEntity.noContent().build();
    }

}
