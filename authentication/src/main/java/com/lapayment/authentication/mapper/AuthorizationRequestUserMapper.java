package com.lapayment.authentication.mapper;

import com.lapayment.authentication.model.User;
import com.lapayment.authentication.payload.AuthorizationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;;

@Mapper(componentModel = "spring")
public interface AuthorizationRequestUserMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "userType", constant = "CUSTOMER"),
            @Mapping(target = "role", constant = "DEFAULT"),
            @Mapping(target = "isAccountNonLocked", constant = "true"),
//            @Mapping(target = "userActivity", ignore = true),
            @Mapping(target = "registeredAt", ignore = true)
    })

    User authorizationRequestToUser(AuthorizationRequest authorizationRequest);
}
