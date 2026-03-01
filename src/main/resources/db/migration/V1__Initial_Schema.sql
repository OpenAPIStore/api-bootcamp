-- V1: Initial Schema
CREATE TABLE vehicle_users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(255),
    profile_image_url VARCHAR(255),
    date_of_birth VARCHAR(255),
    gender VARCHAR(255),
    occupation VARCHAR(255),
    nationality VARCHAR(255),
    government_id VARCHAR(255),
    payment_type VARCHAR(255),
    salaried BOOLEAN,
    bank_name VARCHAR(255),
    account_number VARCHAR(255),
    routing_number VARCHAR(255),
    account_holder_name VARCHAR(255),
    account_type VARCHAR(255),
    comm_address_line1 VARCHAR(255),
    comm_address_line2 VARCHAR(255),
    comm_city VARCHAR(255),
    comm_state VARCHAR(255),
    comm_zip VARCHAR(255),
    ship_address_line1 VARCHAR(255),
    ship_address_line2 VARCHAR(255),
    ship_city VARCHAR(255),
    ship_state VARCHAR(255),
    ship_zip VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    vehicle_condition VARCHAR(50),
    brand VARCHAR(255),
    make VARCHAR(255),
    model VARCHAR(255),
    manufacture_year INTEGER,
    registration_number VARCHAR(255) UNIQUE NOT NULL,
    vin VARCHAR(255) UNIQUE,
    mileage INTEGER,
    body_type VARCHAR(255),
    cost DECIMAL(19, 2),
    engine_capacity INTEGER,
    power INTEGER,
    fuel_type VARCHAR(50),
    has_airbags BOOLEAN,
    has_abs BOOLEAN,
    has_sunroof BOOLEAN,
    has_touchscreen BOOLEAN,
    has_cruise_control BOOLEAN,
    has_parking_sensors BOOLEAN,
    has_rearview_camera BOOLEAN,
    has_alloy_wheels BOOLEAN,
    has_leather_seats BOOLEAN,
    has_keyless_entry BOOLEAN,
    has_push_button_start BOOLEAN,
    has_climate_control BOOLEAN,
    has_bluetooth BOOLEAN,
    has_navigation BOOLEAN,
    has_heated_seats BOOLEAN,
    has_led_headlights BOOLEAN,
    has_power_windows BOOLEAN,
    has_power_steering BOOLEAN,
    has_traction_control BOOLEAN,
    has_hill_assist BOOLEAN,
    transmission_type VARCHAR(100),
    seating_capacity INTEGER,
    doors INTEGER,
    owner_id BIGINT REFERENCES vehicle_users(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE vehicle_images (
    vehicle_id BIGINT NOT NULL REFERENCES vehicles(id),
    image_url VARCHAR(500)
);

CREATE TABLE vehicle_payment_modes (
    vehicle_id BIGINT NOT NULL REFERENCES vehicles(id),
    payment_mode VARCHAR(100)
);

CREATE TABLE vehicle_transactions (
    id BIGSERIAL PRIMARY KEY,
    transaction_type VARCHAR(50) NOT NULL,
    vehicle_id BIGINT NOT NULL REFERENCES vehicles(id),
    user_id BIGINT NOT NULL REFERENCES vehicle_users(id),
    amount DECIMAL(19, 2),
    payment_mode VARCHAR(50),
    transaction_date TIMESTAMP
);

CREATE TABLE vehicle_reviews (
    id BIGSERIAL PRIMARY KEY,
    vehicle_id BIGINT NOT NULL REFERENCES vehicles(id),
    author_id BIGINT NOT NULL REFERENCES vehicle_users(id),
    rating INTEGER NOT NULL,
    comment TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE vehicle_media (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255),
    file_type VARCHAR(100),
    data BYTEA,
    uploaded_at TIMESTAMP
);

CREATE TABLE user_saved_vehicles (
    user_id BIGINT NOT NULL REFERENCES vehicle_users(id),
    vehicle_id BIGINT NOT NULL REFERENCES vehicles(id),
    PRIMARY KEY (user_id, vehicle_id)
);
