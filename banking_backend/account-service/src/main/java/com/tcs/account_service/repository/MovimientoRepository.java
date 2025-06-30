package com.tcs.account_service.repository;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.domain.Entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByNumeroCuenta(String numeroCuenta);
    List<Movimiento> findByNumeroCuentaAndFechaBetween(String numeroCuenta, LocalDateTime desde, LocalDateTime hasta);
}
