package com.agentevirtual.controller;

import com.agentevirtual.dto.LogConversacionDTO;
import com.agentevirtual.model.LogConversacion;
import com.agentevirtual.service.LogConversacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversaciones")
@RequiredArgsConstructor
public class LogConversacionController {

    private final LogConversacionService logConversacionService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarConversacion(@Valid @RequestBody LogConversacion logConversacion) {
        try {
            LogConversacion nuevaConversacion = logConversacionService.registrarConversacion(logConversacion);
            return ResponseEntity.ok(nuevaConversacion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar la conversación: " + e.getMessage());
        }
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<List<LogConversacionDTO>> listarPorIdentificacion(@PathVariable String identificacion) {
        return ResponseEntity.ok(logConversacionService.listarConversacionesPorIdentificacion(identificacion));
    }

    @GetMapping("/ultimas/identificacion/{identificacion}")
    public ResponseEntity<List<LogConversacionDTO>> listarUltimas5PorIdentificacion(@PathVariable String identificacion) {
        return ResponseEntity.ok(logConversacionService.listarUltimas5ConversacionesPorIdentificacion(identificacion));
    }
}
