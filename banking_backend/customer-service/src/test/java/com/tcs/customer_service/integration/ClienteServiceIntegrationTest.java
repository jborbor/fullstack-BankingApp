package com.tcs.customer_service.integration;

import com.tcs.customer_service.domain.enums.Estado;
import com.tcs.customer_service.domain.enums.Genero;
import com.tcs.customer_service.dto.request.ClienteRequestDTO;
import com.tcs.customer_service.dto.response.ClienteResponseDTO;
import com.tcs.customer_service.repository.ClienteRepository;
import com.tcs.customer_service.service.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ClienteServiceIntegrationTest {

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void saveCliente_Success() {
        ClienteRequestDTO dto = new ClienteRequestDTO();
        dto.setNombre("Carlos");
        dto.setGenero(Genero.MASCULINO);
        dto.setEdad(30);
        dto.setIdentificacion("1234567890");
        dto.setDireccion("Calle Falsa 123");
        dto.setTelefono("0987654321");
        dto.setContrasenia("pass123");
        dto.setEstado(Estado.TRUE);

        ClienteResponseDTO saved = clienteService.saveCliente(dto);

        assertThat(saved).isNotNull();
        assertThat(saved.getNombre()).isEqualTo("Carlos");
        assertThat(clienteRepository.existsById(saved.getId())).isTrue();
    }

    @Test
    void getClienteById_NotFound_ThrowsException() {
        Long invalidId = 9999L;
        org.junit.jupiter.api.Assertions.assertThrows(
                com.tcs.customer_service.exception.ResourceNotFoundException.class,
                () -> clienteService.getClienteById(invalidId)
        );
    }

}