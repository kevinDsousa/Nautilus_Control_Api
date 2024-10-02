package com.nautilus_control_api.infra.security.service.impl;

import com.nautilus_control_api.domain.model.dto.request.FirebaseRequestUID;
import com.nautilus_control_api.domain.model.dto.request.TokenRequestJWT;
import com.nautilus_control_api.domain.model.dto.response.TokenResponseJWT;
import com.nautilus_control_api.infra.security.service.AuthService;
import com.nautilus_control_api.infra.security.service.FireBaseAuthService;
import com.nautilus_control_api.infra.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final FireBaseAuthService firebaseAuthService;

    @Override
    public TokenResponseJWT loginWithFirebaseToken(TokenRequestJWT firebaseToken) {
        String uidFromFirebase = firebaseAuthService.getFirebaseUID(firebaseToken.getToken());
        FirebaseRequestUID firebaseUID = FirebaseRequestUID.builder()
                .uid(uidFromFirebase)
                .build();
        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(firebaseUID.getUid());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não registrado!");
        }
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        return TokenResponseJWT.builder()
                .token(jwtService.authenticate(authentication))
                .build();
    }
}
