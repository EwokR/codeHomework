-- liquibase formatted sql

-- changeset AKorneev: 1

CREATE INDEX student_name_index on student (name);

-- changeset AKorneev: 2

CREATE INDEX faculty_name_and_colour_index on faculty (name, colour);
