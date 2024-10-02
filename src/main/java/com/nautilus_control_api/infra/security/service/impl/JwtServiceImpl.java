package com.nautilus_control_api.infra.security.service.impl;

import com.nautilus_control_api.infra.security.model.AuthenticatedUser;
import com.nautilus_control_api.infra.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtEncoder jwtEncoder;

    @Value("${jwt.time.minutes.exp}")
    private int jwtTimeMinutes;

    @Value("${jwt.refresh.time.days.exp}")
    private int jwtRefreshTimeDays;

    public String authenticate(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(60L * jwtTimeMinutes);

        String scope = authentication
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        var claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(expiresAt)
                .issuer("com.nautilus_control_api")
                .subject(authenticatedUser.user().getEmail())
                .claims(buildClaims(authenticatedUser, scope))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(60L * 60 * 24 * jwtRefreshTimeDays);

        var authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        var claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(expiresAt)
                .issuer("com.nautilus_control_api")
                .subject(authenticatedUser.user().getEmail())
                .claim("user_id", authenticatedUser.user().getId())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private Consumer<Map<String, Object>> buildClaims(AuthenticatedUser authenticatedUser, String scope) {
        var userId = authenticatedUser.user().getId();

        return claims -> {
            claims.put("user_id", userId);
            claims.put("email", authenticatedUser.user().getEmail());
            claims.put("username", authenticatedUser.user().getUsername());
            claims.put("scope", scope);
        };
    }
}
