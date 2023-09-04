package com.lapayment.authentication.service.implement;

import com.lapayment.authentication.exception.EmailAlreadyExistsException;
import com.lapayment.authentication.exception.InvalidCredentialsException;
import com.lapayment.authentication.mapper.AuthorizationRequestUserActivityMapperImpl;
import com.lapayment.authentication.mapper.AuthorizationRequestUserMapperImpl;
import com.lapayment.authentication.model.User;
import com.lapayment.authentication.model.UserActivity;
import com.lapayment.authentication.payload.AuthorizationRequest;
import com.lapayment.authentication.payload.AuthorizationResponse;
import com.lapayment.authentication.security.jwt.JwtUtil;
import com.lapayment.authentication.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthorizationRequestUserMapperImpl authorizationRequestUserMapper;
    private final AuthorizationRequestUserActivityMapperImpl authorizationRequestUserActivityMapper;
    private final JwtUtil jwtUtil;
    private final WebClient.Builder webClientBuilder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Mono<AuthorizationResponse> handleRegister(AuthorizationRequest authorizationRequest) {
        return Mono.fromCallable(() -> {
                    User user = authorizationRequestUserMapper.authorizationRequestToUser(authorizationRequest);

                    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                    user.setRegisteredAt(System.currentTimeMillis());
                    return user;
                })
                .flatMap(user -> {
                    // Проверяем существование пользователя по email
                    Mono<Boolean> emailExistsMono = webClientBuilder
                            .baseUrl("http://localhost:8081/api/user/existsByEmail?email=" + user.getEmail())
                            .build()
                            .post()
                            .retrieve()
                            .bodyToMono(Boolean.class);


                    return emailExistsMono.flatMap(emailExists -> {
                        if (emailExists) {
                            return Mono.error(new EmailAlreadyExistsException(
                                    "User with this email already exists"
                            ));
                        } else {
                            UserActivity userActivity =
                                    authorizationRequestUserActivityMapper
                                            .authorizationRequestToUserActivity(authorizationRequest);

                            userActivity.setTimestamp(System.currentTimeMillis());

                            Mono<User> userMono = webClientBuilder
                                    .baseUrl("http://localhost:8081/api/user/save")
                                    .build()
                                    .post()
                                    .bodyValue(user)
                                    .retrieve()
                                    .bodyToMono(User.class)
                                    .cache();

                            Mono<UserActivity> userActivityMono = userMono.flatMap(savedUser -> {

                                userActivity.setUserId(savedUser.getId());

                                return webClientBuilder
                                        .baseUrl("http://localhost:8081/api/userActivity/save")
                                        .build()
                                        .post()
                                        .bodyValue(userActivity)
                                        .retrieve()
                                        .bodyToMono(UserActivity.class)
                                        .cache();
                            });

                            return Mono.zip(userMono, userActivityMono, (savedUser, savedUserActivity) -> savedUser)
                                    .flatMap(savedUser -> jwtUtil.generateToken(savedUser)
                                            .map(AuthorizationResponse::new));
                        }
                    });
                });
    }


    @Override
    public Mono<AuthorizationResponse> handleLogin(AuthorizationRequest authorizationRequest) {
        Mono<User> userMono = webClientBuilder
                .baseUrl("http://localhost:8081/api/user/findByEmailWithUserActivity?email="
                        + authorizationRequest.getEmail())
                .build()
                .post()
                .retrieve()
                .bodyToMono(User.class);

        return userMono.flatMap(user -> {


            if (!bCryptPasswordEncoder.matches(authorizationRequest.getPassword(), user.getPassword())){
                return Mono.error(new InvalidCredentialsException("Invalid login or password"));
            }

            UserActivity userActivity =
                    authorizationRequestUserActivityMapper
                            .authorizationRequestToUserActivity(authorizationRequest);

            userActivity.setUserId(user.getId());
            userActivity.setTimestamp(System.currentTimeMillis());

            Mono<UserActivity> userActivityMono = webClientBuilder
                    .baseUrl("http://localhost:8081/api/userActivity/save")
                    .build()
                    .post()
                    .bodyValue(userActivity)
                    .retrieve()
                    .bodyToMono(UserActivity.class)
                    .cache();

            return Mono.zip(userMono, userActivityMono, (((user1, userActivity1) -> user1)))
                    .flatMap(jwtUtil::generateToken)
                    .map(AuthorizationResponse::new);
        }).switchIfEmpty(Mono.error(new InvalidCredentialsException("Invalid login or password")));
    }

    @Override
    public Mono<User> getInfoByToken(String token) {
        return jwtUtil.isTokenValid(token)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new InvalidCredentialsException("Invalid token"));
                    } else {
                        return jwtUtil.isTokenExpired(token)
                                .flatMap(isExpired -> {
                                    if (isExpired) {
                                        return Mono.error(new InvalidCredentialsException("Invalid token"));
                                    } else {
                                        return jwtUtil.extractUsername(token);
                                    }
                                });
                    }
                })
                .flatMap(username -> webClientBuilder
                        .baseUrl("http://localhost:8081/api/user/findByEmailWithUserActivity?email=" + username)
                        .build()
                        .post()
                        .retrieve()
                        .bodyToMono(User.class));
    }
}
