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

    public ClienteResponseDTO getClienteById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(ClienteResponseDTO.class)
                .block(); // ← Sincróno
    }

    public ClienteResponseDTO getClienteBydentificacion(String identificacion) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/buscar")
                        .queryParam("identificacion", identificacion)
                        .build())
                .retrieve()
                .bodyToMono(ClienteResponseDTO.class)
                .block();
    }

}
