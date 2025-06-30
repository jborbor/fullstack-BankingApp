package com.tcs.account_service.service;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.response.ClienteResponseDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
import com.tcs.account_service.exception.ResourceNotFoundException;
import com.tcs.account_service.mapper.CuentaMapper;
import com.tcs.account_service.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CuentaServiceImpl {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;
    private final ClienteClient clienteClient;

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
            // TODO: Se puede implementar una excepción personalizada o loggear un error
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

}
