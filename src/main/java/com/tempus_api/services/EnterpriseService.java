package com.tempus_api.services;

import com.tempus_api.dtos.EnterpriseDto;
import com.tempus_api.models.Enterprise;
import com.tempus_api.models.User;
import com.tempus_api.models.enums.Roles;
import com.tempus_api.repositories.EnterpriseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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




    public Enterprise updateEnterprise(EnterpriseDto enterpriseDto, UUID id){
        Enterprise enterprise = findById(id);
        if(enterpriseDto.cep() != null){
            enterprise.setCep(enterpriseDto.cep());
        }
        if(enterpriseDto.userId() != null){
            User user = userService.findById(enterpriseDto.userId());
            enterprise.setUser(user);
        }
        if(enterpriseDto.name()!= null){
            enterprise.setName(enterpriseDto.name());
        }
        if(enterpriseDto.cnpj() != null){
            enterprise.setCnpj(enterpriseDto.cnpj());
        }
        return  enterpriseRepository.save(enterprise);
    }

    public Enterprise findById(UUID id){
        return enterpriseRepository.findById(id).orElseThrow(() -> new RuntimeException("cannot be found"));
    }

    public List<Enterprise> findAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getRole().equals(Roles.ADMIN)){
        return enterpriseRepository.findAll();

        }
        return enterpriseRepository.findByUser(user);
    }



    public void deleteEnterprise(UUID id){
        Enterprise enterprise = findById(id);
        enterpriseRepository.delete(enterprise);
    }
}
