package com.tcs.account_service.service;

import com.tcs.account_service.dto.response.ClienteResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class ClienteClient {

    private final WebClient webClient;

    public ClienteClient(@Value("${microservice.clientes.url}") String clientesUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(clientesUrl)
                .build();
    }

    public ClienteResponseDTO getClienteById(Integer id) {
        try {
            return webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(ClienteResponseDTO.class)
                    .block(); // ← Sincrónico
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Cliente no encontrado");
        } catch (Exception e) {
            throw new RuntimeException("Error al consumir el servicio de clientes");
        }
    }

}
