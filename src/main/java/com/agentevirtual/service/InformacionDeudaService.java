package com.agentevirtual.service;

import com.agentevirtual.dto.InformacionDeudaDTO;
import com.agentevirtual.model.InformacionDeuda;
import com.agentevirtual.repository.ClienteRepository;
import com.agentevirtual.repository.InformacionDeudaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InformacionDeudaService {

    private final InformacionDeudaRepository informacionDeudaRepository;
    private final ClienteRepository clienteRepository;

    public InformacionDeuda registrarDeuda(InformacionDeuda deuda) {
        deuda.setEsActivo(true);
        return informacionDeudaRepository.save(deuda);
    }

    public List<InformacionDeuda> listarDeudasPorCliente(Long idCliente) {
        return informacionDeudaRepository.findByCliente_IdCliente(idCliente);
    }

    @Transactional(readOnly = true)
    public List<InformacionDeudaDTO> listarDeudasDTOPorCliente(Long idCliente) {
        return informacionDeudaRepository.buscarDeudasPorIdCliente(idCliente);
    }

    @Transactional(readOnly = true)
    public List<InformacionDeudaDTO> listarDeudasDTOPorIdentificacion(String identificacion) {
        return clienteRepository.findByIdentificacion(identificacion)
                .map(cliente -> informacionDeudaRepository.buscarDeudasPorIdCliente(cliente.getIdCliente()))
                .orElseThrow(() -> new RuntimeException("No se encontró un cliente con esa identificación"));
    }
}

