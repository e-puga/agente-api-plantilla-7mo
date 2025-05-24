package com.agentevirtual.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agentevirtual.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByUsername(String username);
}
