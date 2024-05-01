CREATE TABLE users_cars
(
    user_id         INT,
    name_person     TEXT,
    birth_date      DATE,
    drivers_license BOOLEAN
);
CREATE TABLE cars
(
    car_id    INT,
    car_name  TEXT,
    car_model TEXT,
    price_car SERIAL
);
create TABLE ownership
(
    user_id INT,
    car_id  INT,
    FOREIGN KEY (user_id) REFERENCES cars (car_id)
);