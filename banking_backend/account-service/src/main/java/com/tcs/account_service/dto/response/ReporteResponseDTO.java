package com.tcs.account_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReporteResponseDTO(
        LocalDate fecha,
        String cliente,
        String numeroCuenta,
        String tipo,
        BigDecimal saldoInicial,
        String estado,
        BigDecimal movimiento,
        BigDecimal saldoDisponible
) {}