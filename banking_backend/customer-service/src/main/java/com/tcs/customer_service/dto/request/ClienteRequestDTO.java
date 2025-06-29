package com.tcs.customer_service.dto.request;

import com.tcs.customer_service.domain.enums.Estado;
import com.tcs.customer_service.domain.enums.Genero;
import lombok.Data;

@Data
public class ClienteRequestDTO {

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
