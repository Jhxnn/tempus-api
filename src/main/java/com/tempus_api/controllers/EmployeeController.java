package com.tempus_api.controllers;

import com.tempus_api.dtos.EmployeeDto;
import com.tempus_api.models.Employee;
import com.tempus_api.services.EmployeeService;
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
@RequestMapping("/api/v1/employee")
@Tag(name = "Funcionários", description = "Operações para gerenciamento de funcionários")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Operation(summary = "Listar todos os funcionários de uma empresa")
    @ApiResponse(responseCode = "200", description = "Funcionários listados com sucesso")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<Employee>> findAll(
            @Parameter(description = "ID da empresa") @PathVariable(name = "id") UUID enterpriseId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll(enterpriseId));
    }

    @Operation(summary = "Buscar funcionário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(
            @Parameter(description = "ID do funcionário") @PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findById(id));
    }

    @Operation(summary = "Criar novo funcionário")
    @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso")
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @Parameter(description = "Dados do novo funcionário") @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeDto));
    }

    @Operation(summary = "Atualizar funcionário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @Parameter(description = "Dados atualizados do funcionário") @RequestBody EmployeeDto employeeDto,
            @Parameter(description = "ID do funcionário") @PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.updateEmployee(employeeDto, id));
    }

    @Operation(summary = "Excluir funcionário por ID")
    @ApiResponse(responseCode = "204", description = "Funcionário excluído com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteMapping(
            @Parameter(description = "ID do funcionário") @PathVariable(name = "id") UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
