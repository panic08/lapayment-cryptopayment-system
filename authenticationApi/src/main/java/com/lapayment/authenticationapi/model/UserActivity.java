package com.lapayment.authenticationapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {
    @Id
    private Long id;
    @Column("ipaddress")
    private String ipAddress;
    @Column("browser_name")
    private String browserName;
    @Column("browser_version")
    private String browserVersion;
    @Column("operating_system")
    private String operatingSystem;
    @Column("timestamp")
    private Long timestamp;
    @Column("user_id")
    private Long userId;
}