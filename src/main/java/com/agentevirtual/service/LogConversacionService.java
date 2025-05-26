package com.agentevirtual.service;

import com.agentevirtual.dto.LogConversacionDTO;
import com.agentevirtual.model.LogConversacion;
import com.agentevirtual.repository.LogConversacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogConversacionService {

    private final LogConversacionRepository logConversacionRepository;

    public LogConversacion registrarConversacion(LogConversacion logConversacion) {
        if (logConversacion.getIdentificacion() == null || logConversacion.getIdentificacion().isBlank()) {
            throw new RuntimeException("Debe enviar una identificación válida.");
        }

        logConversacion.setFechaConversacion(LocalDateTime.now());
        return logConversacionRepository.save(logConversacion);
    }

    @Transactional(readOnly = true)
    public List<LogConversacionDTO> listarConversacionesPorIdentificacion(String identificacion) {
        return logConversacionRepository.buscarConversacionesPorIdentificacion(identificacion);
    }

    @Transactional(readOnly = true)
    public List<LogConversacionDTO> listarUltimas5ConversacionesPorIdentificacion(String identificacion) {
        return logConversacionRepository.buscarUltimasConversacionesPorIdentificacion(identificacion)
                .stream()
                .limit(5)
                .collect(Collectors.toList());
    }
}

