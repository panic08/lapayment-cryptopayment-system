package com.lapayment.authenticationapi.model;


import com.lapayment.authenticationapi.enums.Role;
import com.lapayment.authenticationapi.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long id;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
    @Column("user_type")
    private UserType userType;
    @Column("role")
    private Role role;
    @Column("account_non_locked")
    private Boolean isAccountNonLocked;
    @Transient
    private List<UserActivity> userActivity;
    @Column("registered_at")
    private Long registeredAt;
}
