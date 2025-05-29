package com.agentevirtual.controller;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.service.ClienteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClienteWebController {

	@Autowired
	private ClienteService _clienteService;

	@GetMapping("/registro-cliente")
	public String mostrarFormularioCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "registroCliente";
	}
	
	@PostMapping("/guardar-cliente")
	public String guardarCliente(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "registroCliente";
		}

		if (cliente == null) {
			return "registroCliente";
		}

		_clienteService.registrarCliente(cliente, request);

		return "redirect:/ver-clientes";
	}

	@GetMapping("/ver-clientes")
	public String verClientes(Model model) {
		List<Cliente> respCliente = _clienteService.listarClientes();
		model.addAttribute("clientes", respCliente);
		return "verClientes";
	}

	@GetMapping("/editarCliente/{id}")
	public String mostrarFormularioEditarCliente(@PathVariable("id") Integer id, Model model) {
		Cliente cliente = _clienteService.obtenerClientePorId(id);
		model.addAttribute("cliente", cliente);
		model.addAttribute("accion", "/editarRegistroCliente/" + id); // usado por th:action
		return "registroCliente";
	}

	@PostMapping("/editarRegistroCliente/{id}")
	public String actualizarRegistroCliente(@PathVariable("id") int id, @ModelAttribute Cliente cliente) {
		Cliente respCliente = _clienteService.actualizarRegistroCliente(cliente);

		if (respCliente != null) {
			return "redirect:/ver-clientes";
		}
		return "redirect:/registro-cliente";
	}

	@GetMapping("/eliminarRegistroCliente/{id}")
	public String eliminarRegistroCliente(@PathVariable("id") Integer id) {
		Boolean respCliente = _clienteService.eliminarRegistroCliente(id);
		return "redirect:/ver-clientes";
	}
}