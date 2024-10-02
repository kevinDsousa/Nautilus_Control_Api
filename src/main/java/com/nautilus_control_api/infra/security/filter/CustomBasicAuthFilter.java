package com.nautilus_control_api.infra.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CustomBasicAuthFilter extends OncePerRequestFilter {
    @Value("${auth.filter.username}")
    String username;

    @Value("${auth.filter.password}")
    String password;

    private static final int BASIC_LENGTH = 6;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var isAuthentication = "/auth/token".equals(request.getServletPath()) && HttpMethod.POST.matches(request.getMethod());
        var headerAuthorization = request.getHeader("Authorization");

        if (!isAuthentication) {
            filterChain.doFilter(request, response);
            return;
        }

        if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String basicToken = headerAuthorization.substring(BASIC_LENGTH);
        byte[] basicTokenDecoded = Base64.getDecoder().decode(basicToken);
        String basicTokenValue = new String(basicTokenDecoded);
        String[] basicAuthsSplit = basicTokenValue.split(":");

        String username = basicAuthsSplit[0];
        String password = basicAuthsSplit[1];

        if (!(username.equals(this.username) && password.equals(this.password))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }
}
