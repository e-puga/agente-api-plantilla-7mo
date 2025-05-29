package com.agentevirtual.configuracion;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component

public class ManejoError implements AuthenticationFailureHandler {

	@Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String error = "error";

        if (exception instanceof DisabledException) {
            error = "disabled";
        } else if (exception instanceof BadCredentialsException) {
            error = "bad_credentials";
        }

        response.sendRedirect("/login?error=" + error);
    }
}
