package com.lapayment.authentication.service;

import com.lapayment.authentication.model.User;
import com.lapayment.authentication.payload.AuthorizationRequest;
import com.lapayment.authentication.payload.AuthorizationResponse;
import reactor.core.publisher.Mono;

public interface AuthorizationService {
    Mono<AuthorizationResponse> handleRegister(AuthorizationRequest authorizationRequest);
    Mono<AuthorizationResponse> handleLogin(AuthorizationRequest authorizationRequest);
    Mono<User> getInfoByToken(String token);
}
