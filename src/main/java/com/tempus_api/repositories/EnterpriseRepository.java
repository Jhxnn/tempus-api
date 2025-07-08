package com.tempus_api.repositories;

import com.tempus_api.models.Enterprise;
import com.tempus_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID> {

    Enterprise findByUser(User user);
}
