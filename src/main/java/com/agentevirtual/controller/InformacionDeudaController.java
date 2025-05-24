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
@RequiredArgsConstructor
public class InformacionDeudaController {

    private final InformacionDeudaService informacionDeudaService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarDeuda(@Valid @RequestBody InformacionDeuda deuda) {
        try {
            InformacionDeuda nuevaDeuda = informacionDeudaService.registrarDeuda(deuda);
            return ResponseEntity.ok(nuevaDeuda);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar la deuda: " + e.getMessage());
        }
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<List<InformacionDeuda>> listarDeudasPorCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(informacionDeudaService.listarDeudasPorCliente(idCliente));
    }

    @GetMapping("/dto/{idCliente}")
    public ResponseEntity<List<InformacionDeudaDTO>> listarDeudasDTO(@PathVariable Long idCliente) {
        return ResponseEntity.ok(informacionDeudaService.listarDeudasDTOPorCliente(idCliente));
    }
    
    @GetMapping("/dto/identificacion/{identificacion}")
    public ResponseEntity<List<InformacionDeudaDTO>> listarDeudasDTOPorIdentificacion(@PathVariable String identificacion) {
        try {
            List<InformacionDeudaDTO> deudas = informacionDeudaService.listarDeudasDTOPorIdentificacion(identificacion);
            return ResponseEntity.ok(deudas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}


