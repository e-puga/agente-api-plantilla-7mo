package com.agentevirtual.repository;

import com.agentevirtual.dto.InformacionDeudaDTO;
import com.agentevirtual.model.InformacionDeuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InformacionDeudaRepository extends JpaRepository<InformacionDeuda, Long> {
    List<InformacionDeuda> findByCliente_IdCliente(Long idCliente);
    
    @Query("SELECT new com.agentevirtual.dto.InformacionDeudaDTO(d.idInformacionDeuda, d.tipoDeuda, d.descripcion, d.valorTotal, d.valorAPagar, d.totalCuotas, d.numCuotaPagada, d.proximaCuota, d.fechaDeuda, d.fechaMaxPago, d.estadoDeuda, d.esActivo) " +
            "FROM InformacionDeuda d WHERE d.cliente.idCliente = :idCliente")
     List<InformacionDeudaDTO> buscarDeudasPorIdCliente(@Param("idCliente") Long idCliente);
}


