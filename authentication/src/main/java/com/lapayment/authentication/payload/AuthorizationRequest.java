package com.lapayment.authentication.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationRequest {
    @Email(message = "Incorrect e-mail format")
    private String email;
    @NotBlank(message = "Password cannot be less than 5 characters and more than 14")
    @Size(min = 4, max = 14,
            message = "Password cannot be less than 5 characters and more than 14")
    @Pattern(regexp = "^[0-9a-zA-Z]+$",
            message = "The password must consist only of numbers and English alphabet characters")
    private String password;
    @NotNull(message = "Data field is required")
    private Data data;

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        @JsonProperty("ipaddress")
        private String ipAddress;
        private String browserName;
        private String browserVersion;
        private String operatingSystem;
    }
}
