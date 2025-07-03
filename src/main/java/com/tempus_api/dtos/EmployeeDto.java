package com.tempus_api.dtos;

import java.util.UUID;

public record EmployeeDto (String name,
                           String password,
                           double earningHour,
                           UUID enterprise
                           ){
}
