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

    public List<MovimientoResponseDTO> getAllMovimientos() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        return movimientoMapper.toResponseDtoList(movimientos);
    }

    public MovimientoResponseDTO getMovimientoById(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento con id " + id + " no encontrado"));
        return movimientoMapper.toResponseDTO(movimiento);
    }

    public List<MovimientoResponseDTO> getMovimientosByNumeroCuenta(String numeroCuenta) {
        List<Movimiento> movimientos = movimientoRepository.findByNumeroCuenta(numeroCuenta);
        return movimientoMapper.toResponseDtoList(movimientos);
    }

    @Transactional
    public MovimientoResponseDTO saveMovimiento(MovimientoRequestDTO dto) {

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(dto.getNumeroCuenta())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta " + dto.getNumeroCuenta() + " no encontrada"));

        if (dto.getValor().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("El valor del movimiento debe ser diferente de 0.");
        }

        BigDecimal valor = dto.getValor().abs();
        BigDecimal saldoActual = cuenta.getSaldoActual();

        BigDecimal nuevoSaldo;
        if (dto.getTipoMovimiento() == TipoMovimiento.CREDITO) {
            nuevoSaldo = saldoActual.add(valor);
        } else if (dto.getTipoMovimiento() == TipoMovimiento.DEBITO) {
            nuevoSaldo = saldoActual.subtract(valor);
            if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente para retiro");
            }
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no válido");
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setDescripcion(dto.getDescripcion());
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setValor(valor);
        movimiento.setSaldoAnterior(saldoActual);  // si tienes este campo
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setNumeroCuenta(dto.getNumeroCuenta());

        cuenta.setSaldoActual(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimientoRepository.save(movimiento);

        return movimientoMapper.toResponseDTO(movimiento);
    }


}
