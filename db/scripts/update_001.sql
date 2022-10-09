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
    city_id     int references cities(id)
);

CREATE TABLE IF NOT EXISTS candidates
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     timestamp
);
SELECT * FROM posts as p join cities c on c.id = p.city_id