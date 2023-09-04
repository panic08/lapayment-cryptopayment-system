package com.lapayment.authentication.mapper;

import com.lapayment.authentication.dto.UserDto;
import com.lapayment.authentication.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserToUserDtoMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "userType", source = "userType"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "accountNonLocked", source = "accountNonLocked"),
            @Mapping(target = "registeredAt", source = "registeredAt")
    })
    UserDto userToUserDto(User user);
}
