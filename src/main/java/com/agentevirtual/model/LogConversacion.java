package com.agentevirtual.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LogConversacion")
public class LogConversacion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLogConversacion;

    @Column(length = 16)
    private String identificacion;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String logConversacion;

    private LocalDateTime fechaConversacion;

    private Boolean esPersona;
}

