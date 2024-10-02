package com.nautilus_control_api.infra.security.service;

import org.springframework.security.core.Authentication;

public interface JwtService {
    /**
     * Função de autenticação.
     *
     * @param authentication Autenticação do usuário.
     * @return Retorna um token JWT.
     */
    String authenticate(Authentication authentication);

    /**
     * Função para gerar um refresh token
     *
     * @param authentication Autenticação do usuário
     * @return Retorna um refresh token
     */
    String generateRefreshToken(Authentication authentication);
}