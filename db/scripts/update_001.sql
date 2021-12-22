CREATE TABLE if not exists post (
                      id SERIAL PRIMARY KEY,
                      name TEXT NOT NULL
);
CREATE TABLE if not exists candidates (
                      id SERIAL PRIMARY KEY,
                      name TEXT NOT NULL
);
CREATE TABLE if not exists users (
                      id SERIAL PRIMARY KEY,
                      email char (30) NOT NULL UNIQUE,
                      name TEXT NOT NULL,
                      password TEXT NOT NULL
);