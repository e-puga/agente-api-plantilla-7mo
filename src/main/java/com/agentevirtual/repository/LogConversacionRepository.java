package com.agentevirtual.repository;

import com.agentevirtual.dto.LogConversacionDTO;
import com.agentevirtual.model.LogConversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LogConversacionRepository extends JpaRepository<LogConversacion, Long> {

	@Query("SELECT new com.agentevirtual.dto.LogConversacionDTO(l.idLogConversacion, l.identificacion, l.logConversacion, l.fechaConversacion, l.esPersona) FROM LogConversacion l WHERE l.identificacion = :identificacion")
	List<LogConversacionDTO> buscarConversacionesPorIdentificacion(@Param("identificacion") String identificacion);

	@Query("SELECT new com.agentevirtual.dto.LogConversacionDTO(l.idLogConversacion, l.identificacion, l.logConversacion, l.fechaConversacion, l.esPersona) FROM LogConversacion l WHERE l.identificacion = :identificacion ORDER BY l.fechaConversacion DESC")
	List<LogConversacionDTO> buscarUltimasConversacionesPorIdentificacion(@Param("identificacion") String identificacion);

}


