create table if not exists department (
                                          id serial PRIMARY KEY,
                                          name      varchar(100) not null unique
    );

create table if not exists professor (
                                         id serial PRIMARY KEY,
                                         name            varchar(100) not null,
    surname         varchar(30) not null,
    department_id   integer references department(id)
    );

create table if not exists subject (
                                       id serial PRIMARY KEY,
                                       name           varchar(100) not null,
    value_of_hours integer
    );

create table professor_subject (
                                   professor_id integer not null references professor(id),
                                   subject_id   integer not null references subject(id),
                                   primary key (professor_id, subject_id)
);

insert into department (name)
values ('Biology department'),
       ('Journalism department'),
       ('Math department'),
       ('Historical department'),
       ('Department of Mechanics and Mathematics');

insert into professor (name, surname, department_id)
values ('Marina', 'Chigareva', 1),
       ('Anatoly', 'Botogov', 1),
       ('Vitaly', 'Botogov', 1),
       ('Andrey', 'Nagorny', 1),
       ('Yuri', 'Nikitin', 2),
       ('Anna', 'Grankovskaya', 2),
       ('Anton', 'Lebedev', 2),
       ('Andrey', 'Antonevich', 3),
       ('Viktor', 'Gorokhovich', 3),
       ('Valentin', 'Bakhtin', 3),
       ('Sergey', 'Romashchenko', 4),
       ('Sergey', 'Chesalin', 4),
       ('Andrey', 'Lykov', 3),
       ('Konstantin', 'Shtin', 3),
       ('Inna', 'Gutor', 5),
       ('Alexander', 'Bedritsky', 5);

insert into subject (name, value_of_hours)
values ('Plant growing', 36),
       ('Geobotany', 72),
       ('Plant morphology', 32),
       ('Foreign literature', 46),
       ('The history of foreign journalism', 72),
       ('Hydrodynamics', 32),
       ('Mathematical modeling', 46),
       ('The history of political and legal doctrines', 72),
       ('Discrete mathematics', 72);

insert into professor_subject (professor_id, subject_id)
VALUES (1, 1),
       (2, 1),
       (3, 3),
       (3, 2),
       (4, 2),
       (5, 4),
       (5, 5),
       (6, 4),
       (7, 4),
       (7, 5),
       (8, 6),
       (8, 7),
       (9, 6),
       (10, 6),
       (10, 7),
       (13, 6),
       (11, 8),
       (12, 8),
       (14, 6),
       (15, 9),
       (16, 9);