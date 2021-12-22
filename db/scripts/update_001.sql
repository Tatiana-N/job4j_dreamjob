drop table if exists post;
CREATE TABLE if not exists post (
                      id SERIAL PRIMARY KEY,
                      name TEXT NOT NULL
);
drop table if exists candidates;
CREATE TABLE if not exists candidates (
                      id SERIAL PRIMARY KEY,
                      name TEXT NOT NULL
);
drop table if exists users;
CREATE TABLE if not exists users (
                      id SERIAL PRIMARY KEY,
                      email char (30) NOT NULL UNIQUE,
                      name TEXT NOT NULL,
                      password TEXT NOT NULL
);