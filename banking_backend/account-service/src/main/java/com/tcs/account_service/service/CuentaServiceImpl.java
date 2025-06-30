package com.tcs.account_service.service;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.domain.Entity.Movimiento;
import com.tcs.account_service.domain.Enums.TipoMovimiento;
import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.response.ClienteResponseDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
import com.tcs.account_service.dto.response.ReporteResponseDTO;
import com.tcs.account_service.exception.ResourceNotFoundException;
import com.tcs.account_service.mapper.CuentaMapper;
import com.tcs.account_service.repository.CuentaRepository;
import com.tcs.account_service.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CuentaServiceImpl {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;
    private final ClienteClient clienteClient;
    private final MovimientoRepository movimientoRepository;

    @Transactional(readOnly = true)
    public List<CuentaResponseDTO> getAllCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        return cuentaMapper.toResponseDtoList(cuentas);
    }

    @Transactional(readOnly = true)
    public CuentaResponseDTO getCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta con id " + id + " no encontrada"));
        return cuentaMapper.toResponseDTO(cuenta);
    }

    @Transactional(readOnly = true)
    public CuentaResponseDTO getCuentaByNumeroCuenta(String numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta " + numeroCuenta + " no encontrada"));
        return cuentaMapper.toResponseDTO(cuenta);
    }

    @Transactional
    public CuentaResponseDTO saveCuenta(CuentaRequestDTO cuentaRequestDTO) {

        ClienteResponseDTO cliente = clienteClient.getClienteById(cuentaRequestDTO.getClienteId());

        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + cuentaRequestDTO.getClienteId());
        }

        Cuenta cuenta = cuentaMapper.toResponseEntity(cuentaRequestDTO);
        cuentaRepository.save(cuenta);
        return cuentaMapper.toResponseDTO(cuenta);
    }

    @Transactional
    public CuentaResponseDTO deleteCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta con id " + id + " no encontrada"));
        cuentaRepository.deleteById(id);
        return cuentaMapper.toResponseDTO(cuenta);
    }

    @Transactional
    public CuentaResponseDTO updateCuenta(Long id, CuentaRequestDTO cuentaRequestDTO) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta con id " + id + " no encontrada"));

        // Actualizar campos si no son nulos
        if (cuentaRequestDTO.getTipoCuenta() != null) {
            cuenta.setTipoCuenta(cuentaRequestDTO.getTipoCuenta());
        }
        if (cuentaRequestDTO.getEstado() != null) {
            cuenta.setEstado(cuentaRequestDTO.getEstado());
        }

        cuentaRepository.save(cuenta);
        return cuentaMapper.toResponseDTO(cuenta);
    }

    public List<ReporteResponseDTO> generarReporteEstadoCuenta(String identificacion, LocalDate fechaInicio, LocalDate fechaFin) {
        ClienteResponseDTO cliente = clienteClient.getClienteBydentificacion(identificacion);
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(cliente.getId().intValue());

        List<ReporteResponseDTO> reporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository
                    .findByNumeroCuentaAndFechaBetween(
                            cuenta.getNumeroCuenta(),
                            fechaInicio.atStartOfDay(),
                            fechaFin.atTime(23, 59, 59)
                    );

            for (Movimiento mov : movimientos) {
                BigDecimal valorAjustado = TipoMovimiento.DEBITO.toString().equalsIgnoreCase(mov.getTipoMovimiento().toString())
                        ? mov.getValor().negate()
                        : mov.getValor();

                reporte.add(new ReporteResponseDTO(
                        mov.getFecha(),
                        cliente.getNombre(),
                        cuenta.getNumeroCuenta(),
                        cuenta.getTipoCuenta().name(),
                        mov.getSaldoAnterior(),
                        cuenta.getEstado().name(),
                        valorAjustado,
                        mov.getSaldo()
                ));
            }
        }

        return reporte;
    }

}
