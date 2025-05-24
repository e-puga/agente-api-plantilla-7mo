package com.agentevirtual.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogConversacionDTO {
    private Long idLogConversacion;
    private String identificacion;
    private String logConversacion;
    private LocalDateTime fechaConversacion;
    private Boolean esPersona;
}
