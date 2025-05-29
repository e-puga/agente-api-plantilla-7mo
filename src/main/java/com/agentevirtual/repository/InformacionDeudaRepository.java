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

	@Query("SELECT d FROM InformacionDeuda d WHERE d.cliente.id = :idCliente")
	List<InformacionDeuda> buscarDeudasPorIdCliente(@Param("idCliente") int idCliente);
}
