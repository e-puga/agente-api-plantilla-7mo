package com.agentevirtual.service;

import java.util.List;

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

	private final UsuarioRepository _usuarioRepository;
	private final RolRepository _rolRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public Usuario guardarUsuario(Usuario usuario) {
		Usuario usuariodb = _usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
		Rol rolUsuario = _rolRepository.findById(usuario.getIdRolSeleccionado()).orElse(null);

		if (usuariodb == null) {
			usuario.setEnabled(true);
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuario.setRol(rolUsuario);

			usuariodb = usuario;
		} else {
			usuariodb.setUsername(usuario.getUsername());
			usuariodb.setPassword(usuario.getPassword());
			usuariodb.setNombre(usuario.getNombre());
			usuariodb.setApellido(usuario.getApellido());
			usuariodb.setCelular(usuario.getCelular());
			usuariodb.setCorreo(usuario.getCorreo());
			usuariodb.setIdentificacion(usuario.getIdentificacion());
			usuariodb.setEnabled(usuario.isEnabled());
			usuariodb.setRol(rolUsuario);
		}

		return _usuarioRepository.save(usuariodb);
	}

	public List<Usuario> listarUsuarios() {
		return _usuarioRepository.findAll();
	}

	public Usuario obtenerUsuarioPorId(int id) {
		return _usuarioRepository.findById(id).orElse(null);
	}

	public Usuario actualizarRegistroUsuario(Usuario usuarioFormulario) {

		Usuario usuarioBD = _usuarioRepository.findById(usuarioFormulario.getIdUsuario())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		usuarioBD.setNombre(usuarioFormulario.getNombre());
		usuarioBD.setApellido(usuarioFormulario.getApellido());
		usuarioBD.setCorreo(usuarioFormulario.getCorreo());
		usuarioBD.setCelular(usuarioFormulario.getCelular());
		usuarioBD.setIdentificacion(usuarioFormulario.getIdentificacion());
		usuarioBD.setEnabled(usuarioFormulario.isEnabled());

		System.out.println("usuario:" + usuarioFormulario.getIdRolSeleccionado());

		if (usuarioFormulario.getIdRolSeleccionado() != null) {

			Rol nuevoRol = _rolRepository.findById(usuarioFormulario.getIdRolSeleccionado())
					.orElseThrow(() -> new RuntimeException("Rol no encontrado"));
			usuarioBD.setRol(nuevoRol);
		}

		return _usuarioRepository.save(usuarioBD);
	}

	public String resetearPassword(Integer id) {
		Usuario usuario = obtenerUsuarioPorId(id);

		if (usuario == null) {
			return "Error: Usuario no encontrado.";
		}

		String nuevaClave = "12345";
		String claveEncriptada = passwordEncoder.encode(nuevaClave);
		usuario.setPassword(claveEncriptada);

		guardarUsuario(usuario);

		return "Contraseña reseteada correctamente a: " + nuevaClave;
	}

	public List<Rol> listarRoles() {
		return _rolRepository.findAll();
	}

	public Rol obtenerRolPorId(int idRol) {

		return _rolRepository.findById(idRol).orElse(null);
	}
}
