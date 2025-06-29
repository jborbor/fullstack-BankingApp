package com.tcs.customer_service.controller;

import com.tcs.customer_service.dto.request.ClienteRequestDTO;
import com.tcs.customer_service.dto.response.ClienteResponseDTO;
import com.tcs.customer_service.service.ClienteServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteServiceImpl clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes() {
        List<ClienteResponseDTO> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id) {
        ClienteResponseDTO cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO cliente = clienteService.saveCliente(clienteRequestDTO);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> deleteClienteById(@PathVariable Long id) {
        ClienteResponseDTO cliente = clienteService.deleteClienteById(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO task = clienteService.updateCliente(id, clienteRequestDTO);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

}
