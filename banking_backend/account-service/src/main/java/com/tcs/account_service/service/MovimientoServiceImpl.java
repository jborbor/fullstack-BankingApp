package com.tcs.account_service.service;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.domain.Entity.Movimiento;
import com.tcs.account_service.dto.response.ClienteResponseDTO;
import com.tcs.account_service.dto.response.ReporteResponseDTO;
import com.tcs.account_service.mapper.CuentaMapper;
import com.tcs.account_service.repository.CuentaRepository;
import com.tcs.account_service.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovimientoServiceImpl {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteClient clienteClient;





}
