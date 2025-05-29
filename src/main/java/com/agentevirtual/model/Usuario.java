package com.agentevirtual.model;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.agentevirtual.model.Rol;
import com.agentevirtual.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private int idUsuario;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;

	@Column(name = "identificacion")
	private String identificacion;

	@Column(name = "correo")
	private String correo;

	@Column(name = "celular")
	private String celular;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "enabled")
	private boolean enabled;

	@Transient
	private Integer idRolSeleccionado;

	public Integer getIdRolSeleccionado() {
		return idRolSeleccionado;
	}

	public void setIdRolSeleccionado(Integer idRolSeleccionado) {
		this.idRolSeleccionado = idRolSeleccionado;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idRol")
	private Rol rol;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of(new SimpleGrantedAuthority(rol.getNombre()));
	}

	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "UsuarioRol", joinColumns = @JoinColumn(name =
	 * "idUsuario"), inverseJoinColumns = @JoinColumn(name = "idRol")) private
	 * Set<Rol> roles;
	 */

	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * return this.getRoles().stream().map(rol -> new
	 * SimpleGrantedAuthority(rol.getNombre())) .collect(Collectors.toSet()); }
	 */

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
