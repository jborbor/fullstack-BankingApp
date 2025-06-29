package com.tcs.customer_service.domain.entity;

import com.tcs.customer_service.domain.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Persona {

    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}
