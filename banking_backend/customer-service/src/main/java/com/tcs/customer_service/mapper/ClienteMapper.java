package com.tcs.customer_service.mapper;

import com.tcs.customer_service.domain.entity.Cliente;
import com.tcs.customer_service.dto.request.ClienteRequestDTO;
import com.tcs.customer_service.dto.response.ClienteResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ClienteMapper {

    private final ModelMapper modelMapper;

    public ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    public List<ClienteResponseDTO> toResponseDtoList(List<Cliente> cliente) {
        return cliente.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Cliente toResponseEntity(ClienteRequestDTO clienteRequestDTO) {
        return modelMapper.map(clienteRequestDTO, Cliente.class);
    }
}
