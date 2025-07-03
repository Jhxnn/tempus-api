package com.tempus_api.dtos;

import java.util.UUID;

public record EnterpriseDto(String name,
                            String cep,
                            String cnpj,
                            UUID userId){
}
