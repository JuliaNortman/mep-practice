CREATE TABLE keys
(
    prime_key VARCHAR UNIQUE NOT NULL,
    created timestamptz NOT NULL,
    first_access_time timestamptz,
    second_access_time timestamptz,
    first_access_id VARCHAR,
    second_access_id VARCHAR
);