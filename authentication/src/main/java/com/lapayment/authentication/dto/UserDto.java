package com.lapayment.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lapayment.authentication.enums.Role;
import com.lapayment.authentication.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {
    private long id;
    private String email;
    private UserType userType;
    private Role role;
    @JsonProperty("account_non_locked")
    private boolean isAccountNonLocked;
    private long registeredAt;
}
