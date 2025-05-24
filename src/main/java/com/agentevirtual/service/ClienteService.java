package com.agentevirtual.service;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente registrarCliente(Cliente cliente) {
        Optional<Cliente> existente = clienteRepository.findByIdentificacion(cliente.getIdentificacion());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe un cliente con esa identificación.");
        }
        cliente.setEsActivo(true);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorIdentificacion(String identificacion) {
        return clienteRepository.findByIdentificacion(identificacion);
    }
}

