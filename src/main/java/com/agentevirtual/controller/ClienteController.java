package com.agentevirtual.controller;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.service.ClienteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
//@RequiredArgsConstructor
public class ClienteController {

	// @Autowired
	private final ClienteService _clienteService;

	public ClienteController(ClienteService clienteService) {
		_clienteService = clienteService;
	}

	@PostMapping("/registroCliente")
	public Cliente registroCliente(@RequestBody Cliente cliente, HttpServletRequest request) {
		return _clienteService.registrarCliente(cliente, request);
	}

	@GetMapping("/listarClientes")
	public List<Cliente> listarClientes() {
		return _clienteService.listarClientes();
	}

	@GetMapping("/{identificacion}")
	public Cliente obtenerPorIdentificacion(@PathVariable String identificacion) {
		return _clienteService.obtenerPorIdentificacion(identificacion);
	}
}
