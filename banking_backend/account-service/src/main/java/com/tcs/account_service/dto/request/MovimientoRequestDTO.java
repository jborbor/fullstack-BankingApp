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

    private String descripcion;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    private String numeroCuenta;

}
