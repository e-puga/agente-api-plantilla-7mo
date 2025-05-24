package com.agentevirtual.service;

import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agentevirtual.model.Rol;
import com.agentevirtual.model.Usuario;
import com.agentevirtual.repository.RolRepository;
import com.agentevirtual.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UsuarioService {

	
	private final UsuarioRepository usuarioRepository;
	private final RolRepository rolRepository; // Agregado
	private final BCryptPasswordEncoder passwordEncoder;

	public Usuario guardarUsuario(Usuario usuario) {
		// Encriptar contraseña
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		// Habilitar usuario automáticamente
		usuario.setEnabled(true);

		// Buscar rol CLIENTE en la base de datos
		Rol rolCliente = rolRepository.findByNombre("CLIENTE")
				.orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

		// Asignar rol CLIENTE al usuario
		usuario.setRoles(Set.of(rolCliente));

		// Guardar usuario con rol asignado
		return usuarioRepository.save(usuario);
	}
}
