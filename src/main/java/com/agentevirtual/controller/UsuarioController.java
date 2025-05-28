package com.agentevirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.model.Usuario;
import com.agentevirtual.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

	@Autowired
	private UsuarioService _usuarioService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/*
	 * @GetMapping("/registro") public String mostrarFormularioRegistro(Model model)
	 * { model.addAttribute("usuario", new Usuario()); return "registro"; }
	 */

	@PostMapping("/guardar-usuario")
	public String procesarRegistro(@ModelAttribute("usuario") Usuario usuario) {
		_usuarioService.guardarUsuario(usuario);
		return "redirect:/gestion-usuario";
	}

	@GetMapping({ "/", "/home" })
	public String home() {
		return "index";
	}

	@GetMapping("/gestion-usuario")
	public String gestionUsuario(Model model) {
		List<Usuario> usuario = _usuarioService.listarUsuarios();
		model.addAttribute("usuarios", usuario);
		return "verUsuarios";
	}

	@GetMapping("/registro-usuario")
	public String registroUsuario(Model model) {
		model.addAttribute("usuarios", new Usuario());
		return "registroUsuario";
	}

	@GetMapping("/editar-usuario/{id}")
	public String mostrarFormularioEditarUsuario(@PathVariable("id") Integer id, Model model) {
		Usuario usuario = _usuarioService.obtenerUsuarioPorId(id);
		model.addAttribute("usuarios", usuario);
		model.addAttribute("accion", "/editarRegistroUsuario/" + id); // usado por th:action
		return "registroUsuario";
	}

	@PostMapping("/editarRegistroUsuario/{id}")
	public String actualizarRegistroCliente(@PathVariable("id") int id, @ModelAttribute Usuario usuario) {
		Usuario respUsuario = _usuarioService.actualizarRegistroUsuario(usuario);

		if (respUsuario != null) {
			return "redirect:/gestion-usuario";
		}
		return "redirect:/gestion-usuario";
	}

	@GetMapping("/reset-password/{id}")
	public String resetearPasswordUsuario(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
	    String mensaje = _usuarioService.resetearPassword(id);

	    if (mensaje.startsWith("Error")) {
	        redirectAttributes.addFlashAttribute("error", mensaje);
	    } else {
	        redirectAttributes.addFlashAttribute("mensaje", mensaje);
	    }

	    return "redirect:/gestion-usuario";
	}
}
