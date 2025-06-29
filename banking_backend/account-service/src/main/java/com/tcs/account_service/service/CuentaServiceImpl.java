package com.tcs.account_service.service;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.response.ClienteResponseDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
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

    @Transactional
    public CuentaResponseDTO saveCuenta(CuentaRequestDTO cuentaRequestDTO) {

        ClienteResponseDTO cliente = clienteClient.getClienteById(cuentaRequestDTO.getClienteId());

        Cuenta cuenta = cuentaMapper.toResponseEntity(cuentaRequestDTO);
        cuentaRepository.save(cuenta);
        return cuentaMapper.toResponseDTO(cuenta);
    }

}
