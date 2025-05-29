package com.agentevirtual.controller;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.model.InformacionDeuda;
import com.agentevirtual.service.ClienteService;
import com.agentevirtual.service.InformacionDeudaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DeudaWebController {

	@Autowired
	private ClienteService _clienteService;

	@Autowired
	private InformacionDeudaService _informacionDeudaService;

	@GetMapping("/registro-deuda/{idCliente}")
	public String mostrarFormularioDeuda(@PathVariable int idCliente, Model model) {
		Cliente cliente = _clienteService.obtenerClientePorId(idCliente);
		InformacionDeuda informacionDeuda = new InformacionDeuda();
		informacionDeuda.setCliente(cliente);
		model.addAttribute("informacionDeuda", informacionDeuda);
		return "registroDeuda";
	}

	@GetMapping("/actualiza-deuda/{idInformacionDeuda}")
	public String actualizarFormularioDeuda(@PathVariable int idInformacionDeuda, Model model) {
		InformacionDeuda informacionDeuda = _informacionDeudaService.obtenerDeudaPorId(idInformacionDeuda);
		model.addAttribute("informacionDeuda", informacionDeuda);
		return "registroDeuda";
	}

	@GetMapping("/ver-deudas/{idCliente}")
	public String verDeudasPorCliente(@PathVariable int idCliente, Model model) {
		try {
			List<InformacionDeuda> deudas = _informacionDeudaService.listarDeudasPorCliente(idCliente);
			model.addAttribute("deudas", deudas);
			return "verDeudas";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "Error al consultar las deudas");
			return "error";
		}
	}

	@PostMapping("/guardar-deuda")
	public String guardarDeuda(@ModelAttribute InformacionDeuda deuda, Model model) {

		InformacionDeuda respInfoDeuda = _informacionDeudaService.guardarDeuda(deuda);

		if (respInfoDeuda == null) {
			model.addAttribute("error", "transacción no registrada.");
			model.addAttribute("informacionDeuda", deuda);
			return "registroDeuda";
		}
		return "redirect:/ver-deudas/" + deuda.getCliente().getIdCliente();
	}

	@GetMapping("/editarDeuda/{id}")
	public String mostrarFormularioEditarDeuda(@PathVariable("id") Integer id,
			@ModelAttribute InformacionDeuda informacionDeuda, Model model) {
		informacionDeuda = _informacionDeudaService.obtenerDeudaPorId(id);
		model.addAttribute("informacionDeuda", informacionDeuda);
		model.addAttribute("accion", "/editarRegistroDeuda/" + id);
		return "registroDeuda";
	}

	@PostMapping("/editarRegistroDeuda/{id}")
	public String actualizarregistroDeuda(@PathVariable("id") Integer id,
			@ModelAttribute InformacionDeuda informacionDeuda) {
		InformacionDeuda respInfoDeuda = _informacionDeudaService.actualizarRegistroDeuda(informacionDeuda);

		if (respInfoDeuda != null) {
			return "redirect:/ver-deudas/" + respInfoDeuda.getCliente().getIdCliente();
		}
		return "redirect:/ver-deudas/" + informacionDeuda.getCliente().getIdCliente();
	}

	@GetMapping("/eliminarRegistroDeuda/{id}")
	public String eliminarRegistroDeuda(@PathVariable("id") Integer id) {

		Integer respInfoDeuda = _informacionDeudaService.eliminarRegistroDeuda(id);
		return "redirect:/ver-deudas/" + respInfoDeuda;
	}
}
