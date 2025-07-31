package com.tcs.account_service.service;

import com.tcs.account_service.config.RabbitMQConfig;
import com.tcs.account_service.dto.response.ClienteResponseDTO;
import com.tcs.account_service.dto.request.ClienteRequestMessageDTO;
import com.tcs.account_service.dto.response.ClienteResponseMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteRequestService {

    private final RabbitTemplate rabbitTemplate;

    // Mapa para almacenar las respuestas pendientes
    private final ConcurrentHashMap<String, CompletableFuture<ClienteResponseDTO>> pendingRequests =
            new ConcurrentHashMap<>();

    public ClienteResponseDTO sendClienteRequest(Long clienteId) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<ClienteResponseDTO> future = new CompletableFuture<>();

        // Almacenar la promesa pendiente
        pendingRequests.put(correlationId, future);

        // Crear solicitud
        ClienteRequestMessageDTO request = new ClienteRequestMessageDTO();
        request.setClienteId(clienteId);
        request.setCorrelationId(correlationId);
        request.setTimestamp(LocalDateTime.now());

        // Enviar mensaje
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CLIENTE_EXCHANGE,
                RabbitMQConfig.REQUEST_ROUTING_KEY,
                request
        );

        log.info("Solicitud enviada para cliente {} con correlationId: {}",
                clienteId, correlationId);

        // Esperar respuesta con timeout
        return future.handle((response, ex) -> {
            pendingRequests.remove(correlationId);
            if (ex != null) {
                throw new RuntimeException("Error al obtener respuesta del cliente", ex);
            }
            return response;
        }).orTimeout(30, TimeUnit.SECONDS).join();

    }

    @RabbitListener(queues = RabbitMQConfig.CLIENTE_RESPONSE_QUEUE)
    public void handleClienteResponse(ClienteResponseMessageDTO response) {
        String correlationId = response.getCorrelationId();
        log.info("Respuesta recibida para correlationId: {}", correlationId);

        CompletableFuture<ClienteResponseDTO> future = pendingRequests.remove(correlationId);

        if (future != null) {
            if (response.getError() != null) {
                future.completeExceptionally(new RuntimeException(response.getError()));
            } else {
                future.complete(response.getData());
            }
        } else {
            log.warn("Respuesta recibida para correlationId desconocido: {}", correlationId);
        }
    }

}
