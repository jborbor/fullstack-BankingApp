package com.tcs.customer_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseMessageDTO {
    private String correlationId;
    private ClienteResponseDTO data;
    private String error;
    private LocalDateTime timestamp;
}
