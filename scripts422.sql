
CREATE TABLE cars
(
    car_id    INT PRIMARY KEY,
    car_name  TEXT,
    car_model TEXT,
    price_car SERIAL
);
CREATE TABLE users_cars
(
    user_id         INT PRIMARY KEY,
    name_person     TEXT,
    birth_date      DATE,
    drivers_license BOOLEAN,
    car_id INT,
    FOREIGN KEY (car_id) REFERENCES cars(car_id)
);