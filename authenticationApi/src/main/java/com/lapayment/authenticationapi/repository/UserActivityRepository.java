package com.lapayment.authenticationapi.repository;

import com.lapayment.authenticationapi.model.User;
import com.lapayment.authenticationapi.model.UserActivity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserActivityRepository extends ReactiveCrudRepository<UserActivity, Long> {
    @Query("SELECT u.* FROM user_activity u WHERE u.user_id = :user_id ORDER BY u.timestamp DESC LIMIT 30")
    Flux<UserActivity> findByUserId(@Param("user_id") Long userId);
}
