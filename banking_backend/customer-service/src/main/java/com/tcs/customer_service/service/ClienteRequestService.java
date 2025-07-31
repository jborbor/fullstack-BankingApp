package com.tcs.customer_service.service;

import com.tcs.customer_service.dto.request.ClienteRequestMessageDTO;
import com.tcs.customer_service.dto.response.ClienteResponseDTO;
import com.tcs.customer_service.dto.response.ClienteResponseMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import com.tcs.customer_service.config.RabbitMQConfig;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteRequestService {

    private final ClienteServiceImpl clienteService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.CLIENTE_REQUEST_QUEUE)
    public void handleClienteRequest(ClienteRequestMessageDTO request) {
        Long clienteId = request.getClienteId();
        String correlationId = request.getCorrelationId();

        log.info("Solicitud recibida para cliente {} con correlationId: {}",
                clienteId, correlationId);

        try {
            // Buscar cliente en base de datos
            ClienteResponseDTO clienteDTO = clienteService.getClienteById(clienteId);

            // Crear respuesta
            ClienteResponseMessageDTO response = new ClienteResponseMessageDTO();
            response.setCorrelationId(correlationId);
            response.setData(clienteDTO);
            response.setError(clienteDTO == null ? "Cliente no encontrado" : null);
            response.setTimestamp(LocalDateTime.now());

            // Enviar respuesta
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLIENTE_EXCHANGE,
                    RabbitMQConfig.RESPONSE_ROUTING_KEY,
                    response
            );

            log.info("Respuesta enviada para cliente {} con correlationId: {}",
                    clienteId, correlationId);

        } catch (Exception e) {
            log.error("Error procesando solicitud para cliente {}: {}", clienteId, e.getMessage());

            // Enviar respuesta con error
            ClienteResponseMessageDTO errorResponse = new ClienteResponseMessageDTO();
            errorResponse.setCorrelationId(correlationId);
            errorResponse.setData(null);
            errorResponse.setError("Error interno del servidor: " + e.getMessage());
            errorResponse.setTimestamp(LocalDateTime.now());

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLIENTE_EXCHANGE,
                    RabbitMQConfig.RESPONSE_ROUTING_KEY,
                    errorResponse
            );
        }
    }


}
