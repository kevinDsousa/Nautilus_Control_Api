package com.nautilus_control_api.domain.controller.impl;

import com.nautilus_control_api.domain.controller.AuthController;
import com.nautilus_control_api.domain.model.dto.request.TokenRequestJWT;
import com.nautilus_control_api.domain.model.dto.response.TokenResponseJWT;
import com.nautilus_control_api.infra.security.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth", description = "Operações para autenticação dos usuários")
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<TokenResponseJWT> loginWithFirebaseToken(@RequestBody @Valid TokenRequestJWT firebaseToken) {
        return ResponseEntity.ok(authService.loginWithFirebaseToken(firebaseToken));
    }
}

