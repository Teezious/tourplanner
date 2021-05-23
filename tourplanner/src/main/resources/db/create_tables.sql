CREATE TABLE IF NOT EXISTS "tours"
(
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(32) NOT NULL,
    "startpoint" VARCHAR(32),
    "endpoint" VARCHAR(32),
    "description" VARCHAR(256),
    "distance" FLOAT,
    "image" VARCHAR(128)
);