package com.nautilus_control_api.domain.controller;

import com.nautilus_control_api.domain.model.dto.request.TokenRequestJWT;
import com.nautilus_control_api.domain.model.dto.response.TokenResponseJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    @Operation(summary = "Login com firebase-token para resgatar token JWT.", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<TokenResponseJWT> loginWithFirebaseToken(
            @Parameter(description = "Token do FireBase para recuperar os dados associados.") TokenRequestJWT firebaseToken);
}
