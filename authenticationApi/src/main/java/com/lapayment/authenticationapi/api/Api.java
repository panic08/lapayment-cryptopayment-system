package com.lapayment.authenticationapi.api;

import com.lapayment.authenticationapi.model.User;
import com.lapayment.authenticationapi.model.UserActivity;
import com.lapayment.authenticationapi.repository.UserActivityRepository;
import com.lapayment.authenticationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class Api {

    private final UserRepository userRepository;
    private final UserActivityRepository userActivityRepository;

    @PostMapping("/user/save")
    @Transactional
    public Mono<User> save(@RequestBody User user){
        return userRepository.save(user);
    }
    @PostMapping("/userActivity/save")
    @Transactional
    public Mono<UserActivity> save(@RequestBody UserActivity userActivity){
        return userActivityRepository.save(userActivity);
    }

    @PostMapping("/user/existsByEmail")
    @Transactional
    public Mono<Boolean> existsByEmail(@RequestParam("email") String email){
        return userRepository.findByEmail(email)
                .map(user -> true)
                .defaultIfEmpty(false);
    }

    @PostMapping("/user/findByEmailWithUserActivity")
    @Transactional
    public Mono<User> findByEmailWithUserActivity(@RequestParam("email") String email){
        return userRepository.findByEmail(email)
                .flatMap(user -> {
                    Flux<UserActivity> activityFlux = userActivityRepository.findByUserId(user.getId());
                    return activityFlux.collectList().map(userActivities -> {
                        user.setUserActivity(userActivities);
                        return user;
                    });
                });
    }
}
