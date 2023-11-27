package com.jornada_milhas.auth;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jornada_milhas.users.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService service;
    private final UserRepository repository;

    public SecurityFilter(TokenService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null) {
            try {
                repository
                        .findByEmail(service.validateToken(token))
                        .ifPresentOrElse(u -> SecurityContextHolder
                                .getContext()
                                .setAuthentication(new UsernamePasswordAuthenticationToken(
                                        u.getEmail(),
                                        u.getSenha(),
                                        u.getAuthorities())), () -> {
                            throw new NoSuchElementException("Usuário não encontrado");
                        });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token != null
                ? token.substring(7)
                : null;
    }
}
