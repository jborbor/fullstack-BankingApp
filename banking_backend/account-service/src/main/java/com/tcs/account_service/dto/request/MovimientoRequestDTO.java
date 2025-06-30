package com.tcs.account_service.dto.request;

import com.tcs.account_service.domain.Enums.Estado;
import com.tcs.account_service.domain.Enums.TipoCuenta;
import com.tcs.account_service.domain.Enums.TipoMovimiento;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MovimientoRequestDTO {

    private Long id;
    private LocalDateTime fecha;
    private String descripcion;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal saldoAnterior;
    private BigDecimal valor;
    private BigDecimal saldo;
    private String numeroCuenta;

}
