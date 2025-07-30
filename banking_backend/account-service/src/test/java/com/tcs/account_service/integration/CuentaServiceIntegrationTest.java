package com.tcs.account_service.integration;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.domain.Enums.Estado;
import com.tcs.account_service.domain.Enums.TipoCuenta;
import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.repository.CuentaRepository;
import com.tcs.account_service.service.CuentaServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // para rollback después de cada test
public class CuentaServiceIntegrationTest {

    @Autowired
    private CuentaServiceImpl cuentaService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
    void testGuardarCuenta() {
        CuentaRequestDTO dto = new CuentaRequestDTO();
        dto.setClienteId(1L);
        dto.setNumeroCuenta("123456");
        dto.setSaldoInicial(new java.math.BigDecimal("1000"));
        dto.setEstado(Estado.TRUE);
        dto.setTipoCuenta(TipoCuenta.AHORROS);

        var respuesta = cuentaService.saveCuenta(dto);

        assertThat(respuesta).isNotNull();
        assertThat(respuesta.getNumeroCuenta()).isEqualTo("123456");

        Cuenta cuentaGuardada = cuentaRepository.findById(respuesta.getId()).orElse(null);
        assertThat(cuentaGuardada).isNotNull();
        assertThat(cuentaGuardada.getSaldoActual()).isEqualByComparingTo(dto.getSaldoInicial());
    }
}