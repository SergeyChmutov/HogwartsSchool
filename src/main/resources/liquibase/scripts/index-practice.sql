-- liquibase formatted sql

-- changeset schmutov:1
CREATE INDEX name_idx ON student (name);

-- changeset schmutov:2
CREATE INDEX name_color_idx ON faculty (name, color);