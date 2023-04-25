package com.example.Neurosurgical.App.auth;


import com.example.Neurosurgical.App.config.JwtService;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Neurosurgical.App.models.entities.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // TODO: Modificare register register pentru a putea fi folosit de admin! -> adica creare pe 3 cazuri diferite Student, Profesor, Admin
    // TODO: Modificare creare, update
    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .emailFaculty(request.getEmailFaculty())
                .password(encoder.encode(request.getPassword()))
                .role(Role.STUDENT.ordinal())
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }



    // Easy peasy
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailFaculty(),
                        request.getPassword()
                )
        );
        var use = repository.findByFacMail(request.getEmailFaculty());

        var jwtToken = jwtService.generateToken(use);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();

    }
}