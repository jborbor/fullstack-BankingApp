package com.tcs.account_service.controller;

import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
import com.tcs.account_service.service.CuentaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<CuentaResponseDTO> createCuenta(@Valid @RequestBody CuentaRequestDTO cuentaRequestDTO) {
        CuentaResponseDTO cuenta = cuentaService.saveCuenta(cuentaRequestDTO);
        return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
    }
}
