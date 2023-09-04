package com.lapayment.authentication.controller;

import com.lapayment.authentication.model.User;
import com.lapayment.authentication.payload.AuthorizationRequest;
import com.lapayment.authentication.payload.AuthorizationResponse;
import com.lapayment.authentication.service.implement.AuthorizationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://localhost:443")
public class AuthorizationController {

    private final AuthorizationServiceImpl authorizationService;
    @PostMapping("/register")
    private Mono<AuthorizationResponse> register(
           @Valid @RequestBody AuthorizationRequest authorizationRequest
    ){
        return authorizationService.handleRegister(authorizationRequest);
    }

    @PostMapping("/login")
    private Mono<AuthorizationResponse> login(
            @Valid @RequestBody AuthorizationRequest authorizationRequest
    ){
        return authorizationService.handleLogin(authorizationRequest);
    }

    @PostMapping("/getInfoByToken")
    private Mono<User> getInfoByToken(@RequestParam("token") String token){
        return authorizationService.getInfoByToken(token);
    }
}
