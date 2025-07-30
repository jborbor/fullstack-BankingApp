package com.tcs.account_service.service;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.domain.Enums.Estado;
import com.tcs.account_service.domain.Enums.TipoCuenta;
import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.response.ClienteResponseDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
import com.tcs.account_service.exception.ResourceNotFoundException;
import com.tcs.account_service.mapper.CuentaMapper;
import com.tcs.account_service.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private CuentaMapper cuentaMapper;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @Test
    void saveCuenta_Success() {
        // Datos de prueba
        CuentaRequestDTO requestDTO = new CuentaRequestDTO();
        requestDTO.setClienteId(1L);
        requestDTO.setNumeroCuenta("12345");
        requestDTO.setTipoCuenta(TipoCuenta.AHORROS);
        requestDTO.setSaldoInicial(new BigDecimal("1000"));
        requestDTO.setEstado(Estado.TRUE);

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(1L);
        clienteResponseDTO.setNombre("Jonathan");

        Cuenta cuenta = new Cuenta();
        cuenta.setSaldoInicial(requestDTO.getSaldoInicial());

        CuentaResponseDTO cuentaResponseDTO = new CuentaResponseDTO();
        cuentaResponseDTO.setNumeroCuenta(requestDTO.getNumeroCuenta());

        // Configurar mocks
        when(clienteClient.getClienteById(1L)).thenReturn(clienteResponseDTO);
        when(cuentaMapper.toResponseEntity(requestDTO)).thenReturn(cuenta);
        when(cuentaMapper.toResponseDTO(cuenta)).thenReturn(cuentaResponseDTO);
        when(cuentaRepository.save(cuenta)).thenReturn(cuenta);

        // Ejecutar método
        CuentaResponseDTO result = cuentaService.saveCuenta(requestDTO);

        // Verificar
        assertNotNull(result);
        assertEquals("12345", result.getNumeroCuenta());
        verify(cuentaRepository).save(cuenta);
    }

    @Test
    void saveCuenta_ClienteNoExiste_ThrowsException() {
        CuentaRequestDTO requestDTO = new CuentaRequestDTO();
        requestDTO.setClienteId(999L);

        when(clienteClient.getClienteById(999L)).thenReturn(null);

        // Validar excepción o comportamiento esperado
        assertThrows(ResourceNotFoundException.class, () -> cuentaService.saveCuenta(requestDTO));
    }
}