package com.nautilus_control_api.infra.security.service;

import com.nautilus_control_api.domain.model.dto.request.TokenRequestJWT;
import com.nautilus_control_api.domain.model.dto.response.TokenResponseJWT;

public interface AuthService {
    /**
     * Função de logar com firebaseToken.
     * @param firebaseToken Token de autenticação do firebase.
     * @return Retorna um TokenJWT do usuário logado.
     */
    TokenResponseJWT loginWithFirebaseToken(TokenRequestJWT firebaseToken);
}
