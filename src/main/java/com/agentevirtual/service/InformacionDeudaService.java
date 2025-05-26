package com.agentevirtual.service;

import com.agentevirtual.dto.InformacionDeudaDTO;
import com.agentevirtual.model.Cliente;
import com.agentevirtual.model.InformacionDeuda;
import com.agentevirtual.repository.ClienteRepository;
import com.agentevirtual.repository.InformacionDeudaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class InformacionDeudaService {

	@Autowired
	private InformacionDeudaRepository _informacionDeudaRepository;

	/*
	 * @Autowired private ClienteRepository _clienteRepository;
	 */

	public InformacionDeuda registrarDeuda(InformacionDeuda deuda) {
		deuda.setEsActivo(true);
		return _informacionDeudaRepository.save(deuda);
	}

	public List<InformacionDeuda> listarDeudasPorCliente(int idCliente) {
		return _informacionDeudaRepository.findByCliente_IdCliente(idCliente);
	}

	/*
	 * public List<InformacionDeudaDTO> listarDeudasDTOPorCliente(int idCliente) {
	 * return _informacionDeudaRepository.buscarDeudasPorIdCliente(idCliente); }
	 */

	public InformacionDeuda obtenerDeudaPorId(int id) {
		return _informacionDeudaRepository.findById(id).orElse(null);
	}

	public InformacionDeuda actualizarRegistroDeuda(InformacionDeuda deuda) {

		InformacionDeuda respInfoDeuda = obtenerDeudaPorId(deuda.getIdInformacionDeuda());

		if (respInfoDeuda != null) {
			respInfoDeuda.setTipoDeuda(deuda.getTipoDeuda());
			respInfoDeuda.setDescripcion(deuda.getDescripcion());
			respInfoDeuda.setValorTotal(deuda.getValorTotal());
			respInfoDeuda.setValorAPagar(deuda.getValorAPagar());
			respInfoDeuda.setTotalCuotas(deuda.getTotalCuotas());
			respInfoDeuda.setNumCuotaPagada(deuda.getNumCuotaPagada());
			respInfoDeuda.setProximaCuota(deuda.getProximaCuota());
			respInfoDeuda.setFechaDeuda(deuda.getFechaDeuda());
			respInfoDeuda.setFechaMaxPago(deuda.getFechaMaxPago());
			respInfoDeuda.setEstadoDeuda(deuda.getEstadoDeuda());
			respInfoDeuda = _informacionDeudaRepository.save(respInfoDeuda);
		}
		return respInfoDeuda;
	}

	public Integer eliminarRegistroDeuda(int id) {

		Integer respInfoDeuda = 0;
		InformacionDeuda informacionDeuda = _informacionDeudaRepository.findById(id).orElse(null);

		if (informacionDeuda != null) {
			respInfoDeuda = informacionDeuda.getCliente().getIdCliente();
			_informacionDeudaRepository.deleteById(id);
		}
		return respInfoDeuda;
	}

	public InformacionDeuda guardarDeuda(InformacionDeuda deuda) {

		InformacionDeuda respDeuda = _informacionDeudaRepository.findById(deuda.getIdInformacionDeuda()).orElse(null);
		double valorapagar = deuda.getValorTotal() / deuda.getTotalCuotas();

		if (respDeuda == null) {
			respDeuda = deuda;
		}

		respDeuda.setTipoDeuda(deuda.getTipoDeuda());
		respDeuda.setDescripcion(deuda.getDescripcion());
		respDeuda.setValorTotal(deuda.getValorTotal());
		respDeuda.setTotalCuotas(deuda.getTotalCuotas());
		respDeuda.setValorAPagar(valorapagar);
		respDeuda.setNumCuotaPagada(deuda.getNumCuotaPagada());
		respDeuda.setProximaCuota(deuda.getNumCuotaPagada() + 1);
		respDeuda.setFechaDeuda(deuda.getFechaDeuda());
		respDeuda.setFechaMaxPago(deuda.getFechaDeuda().plusMonths(1));

		respDeuda.setEstadoDeuda(deuda.getEstadoDeuda());
		respDeuda.setEsActivo(true);

		return _informacionDeudaRepository.save(respDeuda);
	}

	/*
	 * @Transactional(readOnly = true) public List<InformacionDeudaDTO>
	 * listarDeudasDTOPorCliente(int idCliente) { return
	 * _informacionDeudaRepository.buscarDeudasPorIdCliente(idCliente); }
	 * 
	 * @Transactional(readOnly = true) public List<InformacionDeudaDTO>
	 * listarDeudasDTOPorIdentificacion(String identificacion) { return
	 * _clienteRepository.findByIdentificacion(identificacion) .map(cliente ->
	 * _informacionDeudaRepository.buscarDeudasPorIdCliente(cliente.getIdCliente()))
	 * .orElseThrow(() -> new
	 * RuntimeException("No se encontró un cliente con esa identificación")); }
	 */

}
