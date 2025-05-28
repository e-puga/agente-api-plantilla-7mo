package com.agentevirtual.model;

import com.agentevirtual.model.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRol;

	@Column(unique = true, nullable = false, name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

}
