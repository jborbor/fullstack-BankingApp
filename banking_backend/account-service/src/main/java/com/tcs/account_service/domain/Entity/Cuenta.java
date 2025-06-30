package com.tcs.account_service.domain.Entity;

import com.tcs.account_service.domain.Enums.Estado;
import com.tcs.account_service.domain.Enums.TipoCuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuenta")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", nullable = false, unique = true)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(name = "saldo_inicial", nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "saldo_actual", nullable = false)
    private BigDecimal saldoActual;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "cliente_id", nullable = false)
    private int clienteId;

    @PrePersist
    public void prePersist() {
        this.saldoActual = this.saldoInicial;
    }

}
