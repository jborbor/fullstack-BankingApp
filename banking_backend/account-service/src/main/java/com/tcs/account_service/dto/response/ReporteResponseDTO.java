package com.tcs.account_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReporteResponseDTO(
        LocalDateTime fecha,
        String cliente,
        String numeroCuenta,
        String tipo,
        BigDecimal saldoInicial,
        String estado,
        BigDecimal movimiento,
        BigDecimal saldoDisponible
) {}