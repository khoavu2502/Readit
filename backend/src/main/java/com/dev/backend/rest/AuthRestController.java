package com.dev.backend.rest;

import com.dev.backend.dto.LoginDto;
import com.dev.backend.dto.UserDto;
import com.dev.backend.entity.LoginResponse;
import com.dev.backend.entity.User;
import com.dev.backend.exception.ValidationHandlerException;
import com.dev.backend.service.Impl.AuthenticationService;
import com.dev.backend.service.Impl.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userData) {
        UserDto userDto = authenticationService.register(userData);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto userDto = authenticationService.getCurrentUser();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginDto) {
        User user = authenticationService.authenticate(loginDto);

        String jwtToken = jwtService.generateToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setToken(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }
}
