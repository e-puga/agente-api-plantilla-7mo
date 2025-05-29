package com.agentevirtual.repository;

import com.agentevirtual.dto.LogConversacionDTO;
import com.agentevirtual.model.LogConversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogConversacionRepository extends JpaRepository<LogConversacion, Integer> {

	@Query("SELECT l FROM LogConversacion l WHERE l.identificacion = :identificacion")
	List<LogConversacionDTO> buscarConversacionesPorIdentificacion(@Param("identificacion") String identificacion);

	@Query("SELECT l FROM LogConversacion l WHERE l.identificacion = :identificacion ORDER BY l.fechaConversacion DESC")
	List<LogConversacionDTO> buscarUltimasConversacionesPorIdentificacion(
			@Param("identificacion") String identificacion);
}
