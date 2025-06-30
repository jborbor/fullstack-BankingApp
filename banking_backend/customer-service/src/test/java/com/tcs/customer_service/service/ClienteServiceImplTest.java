package com.tcs.customer_service.service;


import com.tcs.customer_service.domain.entity.Cliente;
import com.tcs.customer_service.domain.enums.Genero;
import com.tcs.customer_service.dto.request.ClienteRequestDTO;
import com.tcs.customer_service.dto.response.ClienteResponseDTO;
import com.tcs.customer_service.exception.ResourceNotFoundException;
import com.tcs.customer_service.mapper.ClienteMapper;
import com.tcs.customer_service.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClientes_ReturnsListOfClienteResponseDTO() {
        // Arrange
        List<Cliente> clientes = List.of(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<ClienteResponseDTO> dtos = List.of(new ClienteResponseDTO(), new ClienteResponseDTO());
        when(clienteMapper.toResponseDtoList(clientes)).thenReturn(dtos);

        // Act
        List<ClienteResponseDTO> result = clienteService.getAllClientes();

        // Assert
        assertThat(result).isEqualTo(dtos);
        verify(clienteRepository).findAll();
        verify(clienteMapper).toResponseDtoList(clientes);
    }

    @Test
    void getClienteById_ExistingId_ReturnsClienteResponseDTO() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteResponseDTO dto = new ClienteResponseDTO();
        when(clienteMapper.toResponseDTO(cliente)).thenReturn(dto);

        ClienteResponseDTO result = clienteService.getClienteById(1L);

        assertThat(result).isEqualTo(dto);
        verify(clienteRepository).findById(1L);
        verify(clienteMapper).toResponseDTO(cliente);
    }

    @Test
    void getClienteById_NotFound_ThrowsResourceNotFoundException() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.getClienteById(99L));
        verify(clienteRepository).findById(99L);
        verifyNoInteractions(clienteMapper);
    }

    @Test
    void saveCliente_Success() {
        ClienteRequestDTO requestDTO = new ClienteRequestDTO();
        Cliente cliente = new Cliente();
        ClienteResponseDTO responseDTO = new ClienteResponseDTO();

        when(clienteMapper.toResponseEntity(requestDTO)).thenReturn(cliente);
        when(clienteMapper.toResponseDTO(cliente)).thenReturn(responseDTO);

        ClienteResponseDTO result = clienteService.saveCliente(requestDTO);

        verify(clienteRepository).save(cliente);
        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    void deleteClienteById_ExistingId_DeletesAndReturnsDTO() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        ClienteResponseDTO dto = new ClienteResponseDTO();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toResponseDTO(cliente)).thenReturn(dto);

        ClienteResponseDTO result = clienteService.deleteClienteById(1L);

        verify(clienteRepository).deleteById(1L);
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void deleteClienteById_NotFound_ThrowsResourceNotFoundException() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.deleteClienteById(2L));
        verify(clienteRepository).findById(2L);
        verify(clienteRepository, never()).deleteById(any());
    }

    @Test
    void updateCliente_ExistingId_UpdatesAndReturnsDTO() {
        Long id = 1L;
        ClienteRequestDTO requestDTO = new ClienteRequestDTO();
        requestDTO.setNombre("Juan");
        requestDTO.setGenero(Genero.MASCULINO);

        Cliente cliente = new Cliente();
        cliente.setId(id);

        ClienteResponseDTO responseDTO = new ClienteResponseDTO();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toResponseDTO(cliente)).thenReturn(responseDTO);

        ClienteResponseDTO result = clienteService.updateCliente(id, requestDTO);

        verify(clienteRepository).save(cliente);
        assertThat(cliente.getNombre()).isEqualTo("Juan");
        assertThat(cliente.getGenero()).isEqualTo(Genero.MASCULINO);
        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    void updateCliente_NotFound_ThrowsResourceNotFoundException() {
        when(clienteRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.updateCliente(3L, new ClienteRequestDTO()));
    }
}