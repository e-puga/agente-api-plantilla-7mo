package com.agentevirtual.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.model.Rol;
import com.agentevirtual.model.Usuario;
import com.agentevirtual.repository.RolRepository;
import com.agentevirtual.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository _usuarioRepository;
	private final RolRepository _rolRepository; // Agregado
	private final BCryptPasswordEncoder passwordEncoder;

	public Usuario guardarUsuario(Usuario usuario) {
		// Encriptar contraseña
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		// Habilitar usuario automáticamente
		usuario.setEnabled(true);

		// Buscar rol CLIENTE en la base de datos
		Rol rolCliente = _rolRepository.findByNombre("GESTOR")
				.orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

		// Asignar rol CLIENTE al usuario
		usuario.setRoles(Set.of(rolCliente));

		// Guardar usuario con rol asignado
		return _usuarioRepository.save(usuario);
	}

	public List<Usuario> listarUsuarios() {
		return _usuarioRepository.findAll();
	}

	public Usuario obtenerUsuarioPorId(int id) {
		return _usuarioRepository.findById(id).orElse(null);
	}

	public Usuario actualizarRegistroUsuario(Usuario usuario) {

		Usuario respUsuario = obtenerUsuarioPorId(usuario.getIdUsuario());

		if (respUsuario != null) {
			respUsuario.setUsername(usuario.getUsername());
			respUsuario.setNombre(usuario.getNombre());
			respUsuario.setApellido(usuario.getApellido());
			respUsuario.setIdentificacion(usuario.getIdentificacion());
			respUsuario.setCorreo(usuario.getCorreo());
			respUsuario.setCelular(usuario.getCelular());
			respUsuario.setPassword(usuario.getPassword());
			respUsuario.setEnabled(usuario.isEnabled());

			respUsuario.setRoles(usuario.getRoles());

			respUsuario = _usuarioRepository.save(respUsuario);
		}
		return respUsuario;
	}

	public String resetearPassword(Integer id) {
		Usuario usuario = obtenerUsuarioPorId(id);

		if (usuario == null) {
			return "Error: Usuario no encontrado.";
		}

		String nuevaClave = "123456";
		String claveEncriptada = passwordEncoder.encode(nuevaClave);
		usuario.setPassword(claveEncriptada);

		guardarUsuario(usuario);

		return "Contraseña reseteada correctamente a: " + nuevaClave;
	}
}
