package com.tcs.customer_service.service;

import com.tcs.customer_service.domain.entity.Cliente;
import com.tcs.customer_service.dto.request.ClienteRequestDTO;
import com.tcs.customer_service.dto.response.ClienteResponseDTO;
import com.tcs.customer_service.mapper.ClienteMapper;
import com.tcs.customer_service.repository.ClienteRepository;
import com.tcs.customer_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteServiceImpl {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clienteMapper.toResponseDtoList(clientes);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + id + " no encontrado"));
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO saveCliente(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteMapper.toResponseEntity(clienteRequestDTO);
        clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO deleteClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + id + " no encontrado"));
        clienteRepository.deleteById(id);
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO updateCliente(Long id, ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + id + " no encontrado"));

        // Actualizar campos si no son nulos
        if (clienteRequestDTO.getNombre() != null) {
            cliente.setNombre(clienteRequestDTO.getNombre());
        }
        if (clienteRequestDTO.getGenero() != null) {
            cliente.setGenero(clienteRequestDTO.getGenero());
        }
        if (clienteRequestDTO.getEdad() != null) {
            cliente.setEdad(clienteRequestDTO.getEdad());
        }
        if (clienteRequestDTO.getIdentificacion() != null) {
            cliente.setIdentificacion(clienteRequestDTO.getIdentificacion());
        }
        if (clienteRequestDTO.getDireccion() != null) {
            cliente.setDireccion(clienteRequestDTO.getDireccion());
        }
        if (clienteRequestDTO.getTelefono() != null) {
            cliente.setTelefono(clienteRequestDTO.getTelefono());
        }
        if (clienteRequestDTO.getContrasenia() != null) {
            cliente.setContrasenia(clienteRequestDTO.getContrasenia());
        }
        if (clienteRequestDTO.getEstado() != null) {
            cliente.setEstado(clienteRequestDTO.getEstado());
        }

        clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(cliente);
    }


}
