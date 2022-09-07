-- liquibase formatted sql

-- changeset kew:1

CREATE TABLE IF NOT EXISTS shelter
(
    shelter_id BIGINT PRIMARY KEY,
    shelter_name VARCHAR (40),
    shelter_adress VARCHAR(255),
    shelter_mappach VARCHAR(255),
    shelter_recomendationpach VARCHAR(255),
    shelter_schedule VARCHAR(255),
    shelter_specification VARCHAR(255),
    shelter_description VARCHAR(255)
);
-- changeset ymalykh:2
ALTER TABLE shelter RENAME COLUMN shelter_mappach to shelter_mappath;
ALTER TABLE shelter RENAME COLUMN shelter_recomendationpach to shelter_recomendationpath;
ALTER TABLE shelter RENAME COLUMN shelter_specification to shelter_specialization;

-- changeset ymalykh:3
ALTER TABLE shelter ADD shelter_security_phone_number VARCHAR(255);
ALTER TABLE shelter DROP COLUMN IF EXISTS id;

-- changeset ymalykh:4
ALTER TABLE shelter ALTER COLUMN shelter_id TYPE BIGINT;

-- changeset ymalykh:5
ALTER TABLE shelter DROP COLUMN IF EXISTS shelter_id;

-- changeset ymalykh:6
ALTER TABLE shelter DROP COLUMN IF EXISTS shelter_id;
ALTER TABLE shelter DROP COLUMN IF EXISTS id;
ALTER TABLE shelter ADD shelter_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY;


