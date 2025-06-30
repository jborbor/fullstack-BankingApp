package com.tcs.account_service.service;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.domain.Entity.Movimiento;
import com.tcs.account_service.domain.Enums.TipoMovimiento;
import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.request.MovimientoRequestDTO;
import com.tcs.account_service.dto.response.ClienteResponseDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
import com.tcs.account_service.dto.response.MovimientoResponseDTO;
import com.tcs.account_service.dto.response.ReporteResponseDTO;
import com.tcs.account_service.exception.ResourceNotFoundException;
import com.tcs.account_service.mapper.CuentaMapper;
import com.tcs.account_service.mapper.MovimientoMapper;
import com.tcs.account_service.repository.CuentaRepository;
import com.tcs.account_service.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovimientoServiceImpl {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;

    @Transactional(readOnly = true)
    public List<MovimientoResponseDTO> getAllMovimientos() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        return movimientoMapper.toResponseDtoList(movimientos);
    }

    @Transactional(readOnly = true)
    public MovimientoResponseDTO getMovimientoById(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento con id " + id + " no encontrado"));
        return movimientoMapper.toResponseDTO(movimiento);
    }

    @Transactional(readOnly = true)
    public List<MovimientoResponseDTO> getMovimientosByNumeroCuenta(String numeroCuenta) {
        List<Movimiento> movimientos = movimientoRepository.findByNumeroCuenta(numeroCuenta);
        return movimientoMapper.toResponseDtoList(movimientos);
    }

    @Transactional
    public MovimientoResponseDTO saveMovimiento(MovimientoRequestDTO movimientoRequestDTO) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoRequestDTO.getNumeroCuenta())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta " + movimientoRequestDTO.getNumeroCuenta() + " no encontrada"));

        movimientoRequestDTO.setValor(movimientoRequestDTO.getValor().abs());

        BigDecimal saldoActual = cuenta.getSaldoActual();

        BigDecimal nuevoSaldo;
        if (TipoMovimiento.CREDITO.toString().equalsIgnoreCase(movimientoRequestDTO.getTipoMovimiento().toString())) {
            nuevoSaldo = saldoActual.add(movimientoRequestDTO.getValor());
        } else if (TipoMovimiento.DEBITO.toString().equalsIgnoreCase(movimientoRequestDTO.getTipoMovimiento().toString())) {
            nuevoSaldo = saldoActual.subtract(movimientoRequestDTO.getValor());
            if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente para retiro");
            }
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no válido");
        }

        movimientoRequestDTO.setSaldoAnterior(saldoActual);
        movimientoRequestDTO.setFecha(LocalDateTime.now());
        movimientoRequestDTO.setSaldo(nuevoSaldo);

        cuenta.setSaldoActual(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = movimientoMapper.toResponseEntity(movimientoRequestDTO);
        movimientoRepository.save(movimiento);
        return movimientoMapper.toResponseDTO(movimiento);
    }


}
