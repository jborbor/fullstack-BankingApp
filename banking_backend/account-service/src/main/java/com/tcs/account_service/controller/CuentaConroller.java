package com.tcs.account_service.controller;

import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
import com.tcs.account_service.dto.response.ReporteResponseDTO;
import com.tcs.account_service.service.CuentaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cuentas")
public class CuentaConroller {

    private final CuentaServiceImpl cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaResponseDTO>> getAllCuentas() {
        List<CuentaResponseDTO> cuentas = cuentaService.getAllCuentas();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> getCuentaById(@PathVariable Long id) {
        CuentaResponseDTO cuenta = cuentaService.getCuentaById(id);
        return ResponseEntity.ok(cuenta);
    }

    @GetMapping("/buscar")
    public ResponseEntity<CuentaResponseDTO> getCuentaByNumeroCuenta(@RequestParam String numeroCuenta) {
        CuentaResponseDTO cuenta = cuentaService.getCuentaByNumeroCuenta(numeroCuenta);
        return ResponseEntity.ok(cuenta);
    }

    @PostMapping
    public ResponseEntity<CuentaResponseDTO> createCuenta(@Valid @RequestBody CuentaRequestDTO cuentaRequestDTO) {
        CuentaResponseDTO cuenta = cuentaService.saveCuenta(cuentaRequestDTO);
        return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> deleteCuentaById(@PathVariable Long id) {
        CuentaResponseDTO cuenta = cuentaService.deleteCuentaById(id);
        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> updateCuenta(@PathVariable Long id, @Valid @RequestBody CuentaRequestDTO cuentaRequestDTO) {
        CuentaResponseDTO cuenta = cuentaService.updateCuenta(id, cuentaRequestDTO);
        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }

    @GetMapping("/reportes")
    public ResponseEntity<List<ReporteResponseDTO>> obtenerReporte(
            @RequestParam String identificacion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<ReporteResponseDTO> reporte = cuentaService.generarReporteEstadoCuenta(identificacion, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
