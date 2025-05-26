package com.agentevirtual.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    @NotBlank(message = "La identificación es obligatoria")
    @Size(min = 10, max = 13, message = "La identificación debe tener entre 10 y 13 caracteres")
    @Column(length = 13, unique = true, nullable = false)
    private String identificacion;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 128)
    @Column(nullable = false)
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 128)
    @Column(nullable = false)
    private String apellidos;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 256)
    @Column(nullable = false)
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos numéricos")
    @Column(length = 10, nullable = false)
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Column(nullable = false)
    private String correo;

    @NotBlank(message = "El estado civil es obligatorio")
    @Size(max = 32)
    @Column(nullable = false)
    private String estadoCivil;

    @Column(nullable = false)
    private Boolean esActivo;

    @CreationTimestamp
    private LocalDateTime fechaRegistro;

    @NotBlank(message = "El usuario de registro es obligatorio")
    @Column(nullable = false)
    private String usuarioRegistro;

    @NotBlank(message = "La estación de registro es obligatoria")
    @Column(nullable = false)
    private String estacionRegistro;
}


