package com.tcs.account_service.mapper;

import com.tcs.account_service.domain.Entity.Cuenta;
import com.tcs.account_service.domain.Entity.Movimiento;
import com.tcs.account_service.dto.request.CuentaRequestDTO;
import com.tcs.account_service.dto.request.MovimientoRequestDTO;
import com.tcs.account_service.dto.response.CuentaResponseDTO;
import com.tcs.account_service.dto.response.MovimientoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MovimientoMapper {

    private final ModelMapper modelMapper;

    public MovimientoResponseDTO toResponseDTO(Movimiento movimiento) {
        return modelMapper.map(movimiento, MovimientoResponseDTO.class);
    }

    public List<MovimientoResponseDTO> toResponseDtoList(List<Movimiento> movimiento) {
        return movimiento.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Movimiento toResponseEntity(MovimientoRequestDTO movimientoRequestDTO) {
        return modelMapper.map(movimientoRequestDTO, Movimiento.class);
    }

}
