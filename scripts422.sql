CREATE TABLE car
(
    id bigint NOT NULL,
    brand text NOT NULL,
    model text NOT NULL,
    cost numeric(15,2) NOT NULL,
    CONSTRAINT pk_car PRIMARY KEY (id)
);

CREATE TABLE driver
(
    id bigint NOT NULL,
    name text NOT NULL,
    age integer NOT NULL,
    has_license boolean NOT NULL,
    car_id bigint NOT NULL,
    CONSTRAINT pk_driver PRIMARY KEY (id),
    CONSTRAINT fk_car FOREIGN KEY (car_id) REFERENCES car (id)
);