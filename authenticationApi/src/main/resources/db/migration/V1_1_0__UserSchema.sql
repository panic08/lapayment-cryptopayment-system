CREATE TABLE IF NOT EXISTS users (
        id SERIAL PRIMARY KEY,
        email VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        user_type VARCHAR(255) NOT NULL,
        role VARCHAR(255) NOT NULL,
        account_non_locked BOOL NOT NULL,
        registered_at BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_activity (
        id SERIAL PRIMARY KEY,
        user_id BIGINT NOT NULL,
        ipaddress VARCHAR(255) NOT NULL,
        browser_name VARCHAR(255) NOT NULL,
        operating_system VARCHAR(255) NOT NULL,
        browser_version VARCHAR(255) NOT NULL,
        timestamp BIGINT NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users (id)
);