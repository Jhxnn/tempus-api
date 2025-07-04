package com.tempus_api.services;

import com.tempus_api.dtos.EnterpriseDto;
import com.tempus_api.models.Enterprise;
import com.tempus_api.models.User;
import com.tempus_api.repositories.EnterpriseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EnterpriseService {

    @Autowired
    EnterpriseRepository enterpriseRepository;

    @Autowired
    UserService userService;

    public Enterprise createEnterprise(EnterpriseDto enterpriseDto){
        Enterprise enterprise = new Enterprise();
        BeanUtils.copyProperties(enterpriseDto, enterprise);
        User user = userService.findById(enterpriseDto.userId());
        enterprise.setUser(user);
        return enterpriseRepository.save(enterprise);
    }

    public Enterprise findById(UUID id){
        return enterpriseRepository.findById(id).orElseThrow(() -> new RuntimeException("cannot be found"));
    }

    public List<Enterprise> findAll() {
        return enterpriseRepository.findAll();
    }

    public void deleteEnterprise(UUID id){
        Enterprise enterprise = findById(id);
        enterpriseRepository.delete(enterprise);
    }
}
