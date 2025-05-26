package com.agentevirtual.controller;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.service.ClienteService;
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

	/*
	 * @PostMapping("/registro") public ResponseEntity<?>
	 * registrarCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
	 * if (result.hasErrors()) { String mensaje = result.getFieldErrors() .stream()
	 * .map(e -> e.getField() + ": " + e.getDefaultMessage()) .reduce("", (a, b) ->
	 * a + b + "; "); return ResponseEntity.badRequest().body(mensaje); }
	 * 
	 * try { Cliente nuevoCliente = _clienteService.registrarCliente(cliente);
	 * return ResponseEntity.ok(nuevoCliente); } catch (RuntimeException e) { return
	 * ResponseEntity.badRequest().body(e.getMessage()); } }
	 */

	/*
	 * @GetMapping() public ResponseEntity<List<Cliente>> listarClientes() { return
	 * ResponseEntity.ok(_clienteService.listarClientes()); }
	 * 
	 * @GetMapping("/{identificacion}") public ResponseEntity<?>
	 * obtenerPorIdentificacion(@PathVariable String identificacion) { Cliente
	 * cliente = _clienteService.obtenerPorIdentificacion(identificacion); return
	 * cliente.<ResponseEntity<?>>map(ResponseEntity::ok) .orElseGet(() ->
	 * ResponseEntity.badRequest().
	 * body("No se encontró el cliente con identificación: " + identificacion)); }
	 */

	@PostMapping("/registroCliente")
	public Cliente registroCliente(@RequestBody Cliente cliente) {
		return _clienteService.registrarCliente(cliente);
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
