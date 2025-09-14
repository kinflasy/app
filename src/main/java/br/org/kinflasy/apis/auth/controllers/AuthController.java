package br.org.kinflasy.apis.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.auth.dto.AuthUser;
import br.org.kinflasy.apis.auth.dto.LoginRequest;
import br.org.kinflasy.apis.auth.dto.LoginResponse;
import br.org.kinflasy.apis.auth.services.TokenService;
import br.org.kinflasy.clients.UserClient;
import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.dto.UserRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "Auth")
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager manager;
    private UserClient client;
    private PasswordEncoder encoder;
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        final var usernamePassword = new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword());

        final var authentication = manager.authenticate(usernamePassword);
        final var token = tokenService.generateToken((AuthUser) authentication.getPrincipal());
        final var response = new LoginResponse().setToken(token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRequest request) {
        // Criptografar a senha
        final var encrypted = encoder.encode(request.getPassword());
        request.setPassword(encrypted);

        return ResponseEntity.ok(client.create(request));
    }

}
