package com.tempus_api.controllers;

import com.tempus_api.dtos.EnterpriseDto;
import com.tempus_api.models.Enterprise;
import com.tempus_api.services.EnterpriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enterprise")
@Tag(name = "Empresas", description = "Operações para gerenciamento de empresas")
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;

    @Operation(summary = "Listar todas as empresas")
    @ApiResponse(responseCode = "200", description = "Empresas retornadas com sucesso")
    @GetMapping
    public ResponseEntity<List<Enterprise>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(enterpriseService.findAll());
    }

    @Operation(summary = "Buscar empresa por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Enterprise> findById(
            @Parameter(description = "ID da empresa") @PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(enterpriseService.findById(id));
    }

    @Operation(summary = "Criar nova empresa")
    @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso")
    @PostMapping
    public ResponseEntity<Enterprise> createEnterprise(
            @Parameter(description = "Dados da nova empresa") @RequestBody EnterpriseDto enterpriseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enterpriseService.createEnterprise(enterpriseDto));
    }

    @Operation(summary = "Atualizar uma empresa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Enterprise> updateEnterprise(
            @Parameter(description = "Dados atualizados da empresa") @RequestBody EnterpriseDto enterpriseDto,
            @Parameter(description = "ID da empresa") @PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enterpriseService.updateEnterprise(enterpriseDto, id));
    }

    @Operation(summary = "Excluir empresa por ID")
    @ApiResponse(responseCode = "204", description = "Empresa excluída com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Enterprise> deleteEnterprise(
            @Parameter(description = "ID da empresa") @PathVariable(name = "id") UUID id) {
        enterpriseService.deleteEnterprise(id);
        return ResponseEntity.noContent().build();
    }
}
