package com.agentevirtual.controller;

import com.agentevirtual.dto.InformacionDeudaDTO;
import com.agentevirtual.model.InformacionDeuda;
import com.agentevirtual.service.InformacionDeudaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deudas")
//@RequiredArgsConstructor
public class InformacionDeudaController {

	private final InformacionDeudaService _informacionDeudaService;

	public InformacionDeudaController(InformacionDeudaService informacionDeudaService) {
		_informacionDeudaService = informacionDeudaService;
	}

	@PostMapping("/registro")
	public ResponseEntity<?> registrarDeuda(@Valid @RequestBody InformacionDeuda deuda) {
		try {
			InformacionDeuda nuevaDeuda = _informacionDeudaService.registrarDeuda(deuda);
			return ResponseEntity.ok(nuevaDeuda);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error al registrar la deuda: " + e.getMessage());
		}
	}

	@GetMapping("/{idCliente}")
	public ResponseEntity<List<InformacionDeuda>> listarDeudasPorCliente(@PathVariable int idCliente) {
		return ResponseEntity.ok(_informacionDeudaService.listarDeudasPorCliente(idCliente));
	}

	@GetMapping("/dto/{idCliente}")
	public ResponseEntity<List<InformacionDeuda>> listarDeudasDTO(@PathVariable int idCliente) {
		return ResponseEntity.ok(_informacionDeudaService.listarDeudasPorCliente(idCliente));
	}

	@GetMapping("/dto/identificacion/{identificacion}")
	public ResponseEntity<List<InformacionDeuda>> listarDeudasPorIdentificacion(@PathVariable String identificacion) {
		try {
			List<InformacionDeuda> deudas = _informacionDeudaService.listarDeudasPorIdentificacion(identificacion);
			return ResponseEntity.ok(deudas);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
