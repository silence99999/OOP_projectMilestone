CREATE TABLE cities (
    id int NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE buildings (
    id int NOT NULL AUTO_INCREMENT,
    street VARCHAR(255),
    house_number int,
    payment_month_per_sq_m int,
    city_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE,
    UNIQUE (city_id, street, house_number)
);

CREATE TABLE rooms (
    id int NOT NULL AUTO_INCREMENT,
    number int,
    area float,
    building_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (building_id) REFERENCES buildings(id) ON DELETE CASCADE,
    UNIQUE (building_id, number)
);