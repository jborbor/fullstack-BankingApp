package com.tcs.account_service.mapper;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CuentaMapper {

    private final ModelMapper modelMapper;

    public CuentaResponseDTO toResponseDTO(Cuenta cuenta) {
        return modelMapper.map(cuenta, CuentaResponseDTO.class);
    }

    public List<CuentaResponseDTO> toResponseDtoList(List<Cuenta> cuenta) {
        return cuenta.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Cuenta toResponseEntity(CuentaRequestDTO cuentaRequestDTO) {
        return modelMapper.map(cuentaRequestDTO, Cuenta.class);
    }
}
