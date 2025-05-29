package com.agentevirtual.service;

import com.agentevirtual.model.Cliente;
import com.agentevirtual.model.InformacionDeuda;
import com.agentevirtual.repository.ClienteRepository;
import com.agentevirtual.repository.InformacionDeudaRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class ClienteService {

	@Autowired
	private ClienteRepository _clienteRepository;

	@Autowired
	private InformacionDeudaRepository _informacionDeudaRepository;

	/*
	 * public Cliente registrarCliente(Cliente cliente) { Optional<Cliente>
	 * existente =
	 * _clienteRepository.findByIdentificacion(cliente.getIdentificacion());
	 * 
	 * if (existente.isPresent()) { throw new
	 * RuntimeException("Ya existe un cliente con esa identificación."); }
	 * 
	 * cliente.setEsActivo(true); cliente.setEsActivo(true); return
	 * _clienteRepository.save(cliente); }
	 */

	public Cliente registrarCliente(Cliente cliente, HttpServletRequest request) {
		Optional<Cliente> existente = _clienteRepository.findByIdentificacion(cliente.getIdentificacion());

		if (existente.isPresent()) {
			throw new RuntimeException("Ya existe un cliente con esa identificación.");
		}

		// Obtener usuario autenticado (Spring Security)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = auth != null ? auth.getName() : "desconocido";

		// Obtener nombre o IP de la estación
		String estacion = request.getRemoteHost(); // o request.getRemoteAddr()

		// String estacion = request.getRemoteAddr();

		// Asignar campos de auditoría
		cliente.setFechaRegistro(LocalDateTime.now());
		cliente.setUsuarioRegistro(nombreUsuario);
		cliente.setEstacionRegistro(estacion);
		cliente.setEsActivo(true);

		return _clienteRepository.save(cliente);
	}
	public List<Cliente> listarClientes() {
	    return _clienteRepository.findByEsActivoTrue();
	    
	    
	    //public List<Cliente> listarClientes() {
  		//return _clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "idCliente"));   
	}

	    
	
	

	public Cliente obtenerPorIdentificacion(String identificacion) {
		return _clienteRepository.findByIdentificacion(identificacion).orElse(null);
	}

	public Cliente guardarCliente(Cliente cliente) {

		Cliente respCliente = _clienteRepository.findByIdentificacion(cliente.getIdentificacion()).orElse(null);

		if (respCliente == null) {
			cliente.setEsActivo(true);
			respCliente = cliente;
		} else {
			respCliente.setIdentificacion(cliente.getIdentificacion());
			respCliente.setNombres(cliente.getNombres());
			respCliente.setApellidos(cliente.getApellidos());
			respCliente.setDireccion(cliente.getDireccion());
			respCliente.setTelefono(cliente.getTelefono());
			respCliente.setCorreo(cliente.getCorreo());
			respCliente.setEstadoCivil(cliente.getEstadoCivil());
			respCliente.setEsActivo(true);
			respCliente.setUsuarioRegistro(cliente.getUsuarioRegistro());
			respCliente.setEstacionRegistro(cliente.getEstacionRegistro());
		}

		respCliente = _clienteRepository.save(respCliente);
		return respCliente;
	}

	public Cliente obtenerClientePorId(int id) {
		return _clienteRepository.findById(id).orElse(null);
	}

	public Cliente actualizarRegistroCliente(Cliente cliente) {

		Cliente respCliente = obtenerClientePorId(cliente.getIdCliente());
		// Cliente respCliente = _clienteRepository.findById(id).orElse(null);

		if (respCliente != null) {
			respCliente.setIdentificacion(cliente.getIdentificacion());
			respCliente.setNombres(cliente.getNombres());
			respCliente.setApellidos(cliente.getApellidos());
			respCliente.setDireccion(cliente.getDireccion());
			respCliente.setTelefono(cliente.getTelefono());
			respCliente.setCorreo(cliente.getCorreo());
			respCliente.setEstadoCivil(cliente.getEstadoCivil());
			respCliente.setEsActivo(true);
			respCliente.setUsuarioRegistro(cliente.getUsuarioRegistro());
			respCliente.setEstacionRegistro(cliente.getEstacionRegistro());
			respCliente = _clienteRepository.save(respCliente);
		}
		return respCliente;
	}

	public Boolean eliminarRegistroCliente(Integer id) {
	    Optional<Cliente> optionalCliente = _clienteRepository.findById(id);
	    if (optionalCliente.isPresent()) {
	        Cliente cliente = optionalCliente.get();
	        List<InformacionDeuda> informacionDeuda = _informacionDeudaRepository.findByCliente_IdCliente(id);

	        if (informacionDeuda == null || informacionDeuda.isEmpty()) {
	            cliente.setEsActivo(false);
	            _clienteRepository.save(cliente);
	            return true;
	        }
	    }
	    return false;
	}




}
