CREATE TABLE IF NOT EXISTS cities
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);
CREATE TABLE IF NOT EXISTS posts
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     timestamp,
    city_id     int references cities (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    varchar(30),
    password TEXT
);

ALTER TABLE users
    ADD CONSTRAINT email_unique UNIQUE (email);

CREATE TABLE IF NOT EXISTS candidates
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     timestamp
);
