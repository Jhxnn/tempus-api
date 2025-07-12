package com.tempus_api.controllers;

import com.tempus_api.dtos.PointDto;
import com.tempus_api.models.Point;
import com.tempus_api.services.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/point")
@Tag(name = "Pontos", description = "Operações relacionadas ao ponto eletrônico")
public class PointController {

    @Autowired
    PointService pointService;

    @Operation(summary = "Buscar todos os pontos de um funcionário")
    @ApiResponse(responseCode = "200", description = "Pontos encontrados com sucesso")
    @GetMapping("/employee/{id}")
    public ResponseEntity<List<Point>> findAll(
            @Parameter(description = "ID do funcionário") @PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.findAll(id));
    }

    @Operation(summary = "Buscar pontos por período e funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pontos encontrados no período"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @GetMapping("/per-date")
    public ResponseEntity<List<Point>> getPointsPerDate(
            @Parameter(description = "Data de início do período (yyyy-MM-dd)")
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

            @Parameter(description = "Data de fim do período (yyyy-MM-dd)")
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,

            @Parameter(description = "ID do funcionário")
            @RequestParam("id") UUID id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.findByDate(startDate, endDate, id));
    }

    @Operation(summary = "Buscar ponto por ID")
    @ApiResponse(responseCode = "200", description = "Ponto encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Point> findById(
            @Parameter(description = "ID do ponto") @PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(pointService.findById(id));
    }

    @Operation(summary = "Criar novo ponto")
    @ApiResponse(responseCode = "201", description = "Ponto criado com sucesso")
    @PostMapping
    public ResponseEntity<Point> createPoint(
            @Parameter(description = "DTO com informações do ponto") @RequestBody PointDto pointDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pointService.createPoint(pointDto));
    }

    @Operation(summary = "Calcular ganhos do funcionario ")
    @ApiResponse(responseCode = "201", description = "Ganhos calculados com sucesso")
    @PostMapping
    public ResponseEntity<BigDecimal> employeeEarnings(
            @Parameter(description = "Lista de pontos") @RequestBody List<Point> points) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pointService.employeeEarnings(points));
    }

    @Operation(summary = "Atualizar um ponto existente")
    @ApiResponse(responseCode = "201", description = "Ponto atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<Point> updatePoint(
            @Parameter(description = "DTO com dados atualizados") @RequestBody PointDto pointDto,
            @Parameter(description = "ID do ponto a ser atualizado") @PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pointService.updatePoint(pointDto, id));
    }

    @Operation(summary = "Excluir ponto por ID")
    @ApiResponse(responseCode = "204", description = "Ponto excluído com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Point> deletePoint(
            @Parameter(description = "ID do ponto") @PathVariable(name = "id") UUID id) {
        pointService.deletePoint(id);
        return ResponseEntity.noContent().build();
    }

}
