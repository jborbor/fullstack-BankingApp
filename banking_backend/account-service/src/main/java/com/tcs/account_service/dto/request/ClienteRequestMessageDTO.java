package com.tcs.account_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestMessageDTO {
    private Long clienteId;
    private String correlationId;
    private LocalDateTime timestamp;
}
