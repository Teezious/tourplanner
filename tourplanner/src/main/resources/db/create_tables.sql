CREATE TABLE IF NOT EXISTS "tours"
(
    "tour_id"       VARCHAR(128) PRIMARY KEY NOT NULL,
    "name"          VARCHAR(32)              NOT NULL,
    "start"         VARCHAR(32),
    "end"           VARCHAR(32),
    "description"   VARCHAR(256),
    "distance"      FLOAT,
    "path_to_image" VARCHAR(128)
);