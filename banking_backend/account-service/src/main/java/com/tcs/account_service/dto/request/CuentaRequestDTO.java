package com.tcs.account_service.dto.request;

import com.tcs.account_service.domain.Enums.Estado;
import com.tcs.account_service.domain.Enums.TipoCuenta;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CuentaRequestDTO {

    private Long id;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private Estado estado;
    private int clienteId;

}
