package com.agentevirtual.controller;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.repository.ClienteRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClienteWebController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/registro-cliente")
    public String mostrarFormularioCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "registroCliente";
    }

    @PostMapping("/guardar-cliente")
    public String guardarCliente(
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult bindingResult,
            Model model) {

        // Validaciones básicas
        if (bindingResult.hasErrors()) {
            return "registroCliente";
        }

        // Verificar duplicidad de identificacion
        if (clienteRepository.findByIdentificacion(cliente.getIdentificacion()).isPresent()) {
            model.addAttribute("errorIdentificacion", "La identificación ya está registrada.");
            return "registroCliente";
        }

        cliente.setEsActivo(true);
        clienteRepository.save(cliente);
        return "redirect:/ver-clientes";
    }

    @GetMapping("/ver-clientes")
    public String verClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "verClientes";
    }
}

