package com.lapayment.authentication.mapper;

import com.lapayment.authentication.model.User;
import com.lapayment.authentication.model.UserActivity;
import com.lapayment.authentication.payload.AuthorizationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AuthorizationRequestUserActivityMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "ipAddress", source = "data.ipAddress"),
            @Mapping(target = "browserName", source = "data.browserName"),
            @Mapping(target = "browserVersion", source = "data.browserVersion"),
            @Mapping(target = "operatingSystem", source = "data.operatingSystem"),
            @Mapping(target = "timestamp", ignore = true),
            @Mapping(target = "userId", ignore = true)
    })
    UserActivity authorizationRequestToUserActivity(AuthorizationRequest authorizationRequest);
}
