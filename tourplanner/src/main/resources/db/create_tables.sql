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

CREATE TABLE IF NOT EXISTS "logs"
(
    "id" SERIAL PRIMARY KEY,
    "fk_tour_id" INT NOT NULL,
    "date" DATE,
    "time" TIME,
    "distance" FLOAT,
    "rating" INT,
    "avg_speed" FLOAT,
    "breaks" INT,
    "degrees" INT,
    "weather" INT,
    "notes" VARCHAR(256),
    CONSTRAINT fk_tour_id_log
        FOREIGN KEY (fk_tour_id)
            REFERENCES tours(id)
            ON DELETE CASCADE
);
