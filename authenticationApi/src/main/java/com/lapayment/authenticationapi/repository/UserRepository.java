package com.lapayment.authenticationapi.repository;

import com.lapayment.authenticationapi.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    @Query("SELECT DISTINCT u.* FROM Users u WHERE u.email = :email")
    Mono<User> findByEmail(@Param("email") String email);
}
