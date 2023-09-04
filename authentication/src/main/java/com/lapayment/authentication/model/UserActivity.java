package com.lapayment.authentication.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {
    private Long id;
    private String ipAddress;
    private String browserName;
    private String browserVersion;
    private String operatingSystem;
    private Long timestamp;
    private Long userId;
}