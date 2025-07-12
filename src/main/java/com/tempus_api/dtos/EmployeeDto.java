package com.tempus_api.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record EmployeeDto (String name,
                           String password,
                           BigDecimal earningHour,
                           UUID enterpriseId
                           ){
}
