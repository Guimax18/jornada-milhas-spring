package com.jornada_milhas.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jornada_milhas.users.UserRepository;
import com.jornada_milhas.users.Users;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository repository;
    private final AuthenticationConfiguration configuration;
    private final TokenService tokenService;

    public AuthService(UserRepository repository, AuthenticationConfiguration configuration,
            TokenService tokenService) {
        this.repository = repository;
        this.configuration = configuration;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public String login(AuthDto dto) throws Exception {
        return tokenService
                .generateToken((Users) configuration.getAuthenticationManager()
                        .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()))
                        .getPrincipal());
    }

    public String register(Users user) throws Exception {
        var optional = repository.findByEmail(user.getEmail());

        optional.ifPresent(u -> {
            throw new IllegalArgumentException("Usuário já cadastrado");
        });

        AuthDto dto = new AuthDto(user.getEmail(), user.getSenha());

        user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
        repository.save(user);

        return login(dto);
    }
}
