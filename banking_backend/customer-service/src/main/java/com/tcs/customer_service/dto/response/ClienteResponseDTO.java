package com.tcs.customer_service.dto.response;

import com.tcs.customer_service.domain.enums.Estado;
import com.tcs.customer_service.domain.enums.Genero;
import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String nombre;
    private Genero genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private String contrasenia;
    private Estado estado;
}
