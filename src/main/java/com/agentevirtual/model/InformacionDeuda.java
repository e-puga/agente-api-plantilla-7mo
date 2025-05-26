package com.agentevirtual.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "InformacionDeuda")
public class InformacionDeuda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idInformacionDeuda;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "idCliente")
	private Cliente cliente;

	@NotBlank
	@Column(length = 128)
	private String tipoDeuda;

	@NotBlank
	@Column(length = 256)
	private String descripcion;

	@NotNull
	private double valorTotal;

	@NotNull
	private double valorAPagar;

	@NotNull
	private Integer totalCuotas;

	@NotNull
	private Integer numCuotaPagada;

	@NotNull
	private Integer proximaCuota;

	@NotNull
	private LocalDateTime fechaDeuda;

	@NotNull
	private LocalDateTime fechaMaxPago;

	@NotBlank
	@Column(length = 64)
	private String estadoDeuda;

	private Boolean esActivo;
}
