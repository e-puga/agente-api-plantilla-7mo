package com.agentevirtual.repository;

import com.agentevirtual.model.InformacionDeuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformacionDeudaRepository extends JpaRepository<InformacionDeuda, Integer> {

    List<InformacionDeuda> findByCliente_IdCliente(int idCliente);

    List<InformacionDeuda> findByCliente_IdClienteAndEsActivoTrue(int idCliente);
   
    @Query("SELECT d FROM InformacionDeuda d WHERE d.cliente.idCliente = :idCliente AND d.esActivo = true")
    List<InformacionDeuda> buscarDeudasActivasPorIdCliente(@Param("idCliente") int idCliente);
    
    @Query("SELECT d FROM InformacionDeuda d WHERE d.cliente.idCliente = :idCliente")
    List<InformacionDeuda> buscarDeudasPorIdCliente(@Param("idCliente") int idCliente);

	
	/*
	 * @Query("SELECT new com.agentevirtual.dto.InformacionDeudaDTO(d.idInformacionDeuda, d.tipoDeuda, d.descripcion, d.valorTotal, d.valorAPagar, d.totalCuotas, d.numCuotaPagada, d.proximaCuota, d.fechaDeuda, d.fechaMaxPago, d.estadoDeuda, d.esActivo) "
	 * + "FROM InformacionDeuda d WHERE d.cliente.idCliente = :idCliente")
	 * List<InformacionDeudaDTO> buscarDeudasPorIdCliente(@Param("idCliente") int
	 * idCliente);
	 */

	/*
	 * @Query("SELECT new com.agentevirtual.dto.InformacionDeudaDTO(" +
	 * "d.idInformacionDeuda, d.tipoDeuda, d.descripcion, d.valorTotal, d.valorAPagar, "
	 * +
	 * "d.totalCuotas, d.numCuotaPagada, d.proximaCuota, d.fechaDeuda, d.fechaMaxPago, "
	 * + "d.estadoDeuda, d.esActivo) " +
	 * "FROM InformacionDeuda d WHERE d.cliente.id = :idCliente")
	 * List<InformacionDeudaDTO> buscarDeudasPorIdCliente(@Param("idCliente") int
	 * idCliente);
	 */

}
