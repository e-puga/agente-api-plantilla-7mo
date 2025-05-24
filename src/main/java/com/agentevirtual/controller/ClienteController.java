package com.agentevirtual.controller;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            String mensaje = result.getFieldErrors()
                    .stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .reduce("", (a, b) -> a + b + "; ");
            return ResponseEntity.badRequest().body(mensaje);
        }

        try {
            Cliente nuevoCliente = clienteService.registrarCliente(cliente);
            return ResponseEntity.ok(nuevoCliente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<?> obtenerPorIdentificacion(@PathVariable String identificacion) {
        Optional<Cliente> cliente = clienteService.obtenerPorIdentificacion(identificacion);
        return cliente.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("No se encontró el cliente con identificación: " + identificacion));
    }
}

