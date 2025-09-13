package br.org.kinflasy.apis.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.auth.dto.LoginRequest;
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

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request) {
        final var usernamePassword = new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword());

        manager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRequest request) {
        // TODO: incluir lógica para identificar se usuário já é cadastrado

        // Criptografar a senha
        final var encrypted = encoder.encode(request.getPassword());
        request.setPassword(encrypted);

        return ResponseEntity.ok(client.create(request));
    }

}
