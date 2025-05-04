package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.shchff.superevent_backend.config.security.JwtTokenUtil;
import ru.shchff.superevent_backend.config.security.UserDetailsServiceImpl;
import ru.shchff.superevent_backend.dto.AuthRequest;
import ru.shchff.superevent_backend.dto.AuthResponse;
import ru.shchff.superevent_backend.dto.RegisterRequestDto;
import ru.shchff.superevent_backend.services.UserService;
import ru.shchff.superevent_backend.util.UserAlreadyExistsException;
import ru.shchff.superevent_backend.util.ErrorResponse;
import ru.shchff.superevent_backend.util.UserNotCreatedException;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "API для аутентификации и управления пользователями")
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponse(responseCode = "200", description = "Регистрация успешна")
    @ApiResponse(responseCode = "405", description = "Пользователь с таким email уже существует")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequestDto request,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
        {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
            {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new UserNotCreatedException(errorMessage.toString());
        }

        userService.registerUser(request);

        return ResponseEntity.ok(generateAuthResponse(request.getEmail()));
    }

    @Operation(summary = "Аутентификация пользователя")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        return ResponseEntity.ok(generateAuthResponse(request.getEmail()));
    }

    private AuthResponse generateAuthResponse(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token);
    }

    private ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException e)
    {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        e.getMessage(),
                        System.currentTimeMillis()
                ));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotCreatedException e)
    {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(
                    e.getMessage(),
                    System.currentTimeMillis()
            ));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserAlreadyExistsException e)
    {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(
                    e.getMessage(),
                    System.currentTimeMillis()
            ));
    }
}
