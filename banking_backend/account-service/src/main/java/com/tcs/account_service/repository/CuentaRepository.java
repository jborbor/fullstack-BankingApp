package com.tcs.account_service.repository;

import com.tcs.account_service.domain.Entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
