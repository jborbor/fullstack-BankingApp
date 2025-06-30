package com.tcs.account_service.controller;

import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.request.MovimientoRequestDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
import com.tcs.account_service.dto.response.MovimientoResponseDTO;
import com.tcs.account_service.service.CuentaServiceImpl;
import com.tcs.account_service.service.MovimientoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoServiceImpl movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoResponseDTO>> getAllMovimientos() {
        List<MovimientoResponseDTO> movimientos = movimientoService.getAllMovimientos();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> getMovimientoById(@PathVariable Long id) {
        MovimientoResponseDTO movimiento = movimientoService.getMovimientoById(id);
        return ResponseEntity.ok(movimiento);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MovimientoResponseDTO>> getMovimientosByNumeroCuenta(@RequestParam String numeroCuenta) {
        List<MovimientoResponseDTO> movimientos = movimientoService.getMovimientosByNumeroCuenta(numeroCuenta);
        return ResponseEntity.ok(movimientos);
    }

    @PostMapping
    public ResponseEntity<MovimientoResponseDTO> createMovimiento(@Valid @RequestBody MovimientoRequestDTO movimientoRequestDTO) {
        MovimientoResponseDTO movimiento = movimientoService.saveMovimiento(movimientoRequestDTO);
        return new ResponseEntity<>(movimiento, HttpStatus.CREATED);
    }


}
