package com.agentevirtual.controller;

import com.agentevirtual.dto.InformacionDeudaDTO;
import com.agentevirtual.model.Cliente;
import com.agentevirtual.model.InformacionDeuda;
import com.agentevirtual.repository.ClienteRepository;
import com.agentevirtual.repository.InformacionDeudaRepository;
import com.agentevirtual.service.InformacionDeudaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DeudaWebController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private InformacionDeudaRepository informacionDeudaRepository;
    
    @Autowired
    private InformacionDeudaService informacionDeudaService;


    @GetMapping("/registro-deuda")
    public String mostrarFormularioDeuda(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "registroDeuda";
    }
    
    @GetMapping("/ver-deudas/{idCliente}")
    public String verDeudasPorCliente(@PathVariable Long idCliente, Model model) {
    	List<InformacionDeudaDTO> deudas = informacionDeudaService.listarDeudasDTOPorCliente(idCliente);
        model.addAttribute("deudas", deudas);
        return "verDeudas";
    }


    @PostMapping("/guardar-deuda")
    public String guardarDeuda(@RequestParam("identificacionCliente") String identificacion,
                              @ModelAttribute InformacionDeuda deuda,
                              Model model) {

        Optional<Cliente> clienteOpt = clienteRepository.findByIdentificacion(identificacion);

        if (clienteOpt.isEmpty()) {
            model.addAttribute("error", "Cliente no encontrado con identificación: " + identificacion);
            // También envía clientes para el formulario si usas select en otro lugar o para refrescar datos
            model.addAttribute("clientes", clienteRepository.findAll());
            return "registroDeuda";
        }

        deuda.setCliente(clienteOpt.get());
        deuda.setEsActivo(true);
        informacionDeudaRepository.save(deuda);

        // Redirige o muestra mensaje de éxito según prefieras
        return "redirect:/registro-deuda";
    }
}

