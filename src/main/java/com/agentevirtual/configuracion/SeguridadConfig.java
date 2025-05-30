package com.agentevirtual.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.agentevirtual.service.ServicioDetalleUsuario;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SeguridadConfig {

	@Autowired
	private ServicioDetalleUsuario servicioDetalleUsuario;
	private final ManejoError failureHandler;

	@Bean
	public DaoAuthenticationProvider proveedorAutenticacion() {
		DaoAuthenticationProvider proveedor = new DaoAuthenticationProvider();
		proveedor.setUserDetailsService(servicioDetalleUsuario);
		proveedor.setPasswordEncoder(codificadorPassword());
		return proveedor;
	}

	// Seguridad para la API REST
	@Bean
	@Order(1)
	public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
		http.securityMatcher("/api/**").csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/**").authenticated()).httpBasic(); // Requiere
																												// autenticación
																												// básica
		return http.build();
	}

	// Seguridad para la web (formularios y HTML)
	@Bean
	@Order(2)
	public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
		http.authenticationManager(authenticationManager())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login", "/registro", "/css/**", "/js/**", "/assets/**", "/fonts/**")
						.permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").failureHandler(failureHandler)
						// .failureUrl("/login?error")
						.defaultSuccessUrl("/home", true).permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll());

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder codificadorPassword() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(proveedorAutenticacion());
	}

}
