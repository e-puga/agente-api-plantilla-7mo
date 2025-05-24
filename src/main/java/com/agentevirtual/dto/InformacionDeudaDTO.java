package com.agentevirtual.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformacionDeudaDTO {
    private Long idInformacionDeuda;
    private String tipoDeuda;
    private String descripcion;
    private BigDecimal valorTotal;
    private BigDecimal valorAPagar;
    private Integer totalCuotas;
    private Integer numCuotaPagada;
    private Integer proximaCuota;
    private LocalDateTime fechaDeuda;
    private LocalDateTime fechaMaxPago;
    private String estadoDeuda;
    private Boolean esActivo;
}
