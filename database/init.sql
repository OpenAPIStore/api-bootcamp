-- Create Users (Passwords are hashed 'password123' using bcrypt)
INSERT INTO vehicle_users (first_name, last_name, email, password, phone_number, profile_image_url, date_of_birth, gender, occupation, nationality, government_id, payment_type, salaried, comm_address_line1, comm_city, comm_state, comm_zip, bank_name, account_number, created_at, updated_at) VALUES 
('Alice', 'Smith', 'alice@example.com', '$2a$10$7Q2V8s8.M3vM/2u8K4wG4OTv9yM9.yK.8uP4u.7/G6M89q56y/u3m', '1234567890', 'https://example.com/alice.jpg', '1990-01-01', 'Female', 'Engineer', 'US', 'SSN-123', 'CREDIT_CARD', true, '123 Main St', 'New York', 'NY', '10001', 'Chase', 'CHK123', NOW(), NOW()),
('Bob', 'Johnson', 'bob@example.com', '$2a$10$7Q2V8s8.M3vM/2u8K4wG4OTv9yM9.yK.8uP4u.7/G6M89q56y/u3m', '0987654321', 'https://example.com/bob.jpg', '1985-05-15', 'Male', 'Doctor', 'UK', 'NIN-456', 'DEBIT_CARD', true, '456 High St', 'London', 'LND', 'SW1A', 'Barclays', 'SAV456', NOW(), NOW());

-- Seed 10 Cars
INSERT INTO vehicles (type, status, vehicle_condition, brand, make, model, manufacture_year, registration_number, vin, mileage, body_type, cost, engine_capacity, power, fuel_type, has_airbags, has_abs, has_sunroof, has_touchscreen, transmission_type, seating_capacity, doors, created_at, updated_at) VALUES
('CAR', 'AVAILABLE', 'NEW', 'Toyota', 'Toyota', 'Camry', 2023, 'CAR-001', 'VIN1234567890C01', 0, 'Sedan', 25000.00, 2500, 203, 'PETROL', true, true, false, true, 'Automatic', 5, 4, NOW(), NOW()),
('CAR', 'AVAILABLE', 'USED', 'Honda', 'Honda', 'Civic', 2022, 'CAR-002', 'VIN1234567890C02', 15000, 'Sedan', 24000.00, 2000, 158, 'PETROL', true, true, true, true, 'CVT', 5, 4, NOW(), NOW()),
('CAR', 'AVAILABLE', 'USED', 'Ford', 'Ford', 'Mustang', 2021, 'CAR-003', 'VIN1234567890C03', 25000, 'Coupe', 27000.00, 2300, 310, 'PETROL', true, true, false, true, 'Manual', 4, 2, NOW(), NOW()),
('CAR', 'AVAILABLE', 'USED', 'Chevrolet', 'Chevrolet', 'Mustang', 2021, 'CAR-004', 'VIN1234567890C04', 30000, 'Coupe', 35000.00, 2300, 310, 'PETROL', true, true, false, true, 'Manual', 4, 2, NOW(), NOW()),
('CAR', 'AVAILABLE', 'NEW', 'Tesla', 'Tesla', 'Model 3', 2024, 'CAR-005', 'VIN1234567890C05', 100, 'Sedan', 40000.00, 0, 283, 'ELECTRIC', true, true, true, true, 'Automatic', 5, 4, NOW(), NOW()),
('CAR', 'AVAILABLE', 'CPO', 'BMW', 'BMW', '3 Series', 2023, 'CAR-006', 'VIN1234567890C06', 5000, 'Sedan', 42000.00, 2000, 255, 'PETROL', true, true, true, true, 'Automatic', 5, 4, NOW(), NOW()),
('CAR', 'AVAILABLE', 'CPO', 'Audi', 'Audi', 'A4', 2023, 'CAR-007', 'VIN1234567890C07', 6000, 'Sedan', 41000.00, 2000, 201, 'PETROL', true, true, true, true, 'Automatic', 5, 4, NOW(), NOW()),
('CAR', 'AVAILABLE', 'USED', 'Mercedes', 'Mercedes', 'C-Class', 2022, 'CAR-008', 'VIN1234567890C08', 20000, 'Sedan', 43000.00, 2000, 255, 'PETROL', true, true, true, true, 'Automatic', 5, 4, NOW(), NOW()),
('CAR', 'AVAILABLE', 'CPO', 'Lexus', 'Lexus', 'IS', 2023, 'CAR-009', 'VIN1234567890C09', 8000, 'Sedan', 40500.00, 2000, 241, 'PETROL', true, true, true, true, 'Automatic', 5, 4, NOW(), NOW()),
('CAR', 'AVAILABLE', 'USED', 'Hyundai', 'Hyundai', 'Accord', 2022, 'CAR-010', 'VIN1234567890C10', 12000, 'Sedan', 26000.00, 1500, 192, 'PETROL', true, true, true, true, 'CVT', 5, 4, NOW(), NOW());

-- Seed 10 Bikes
INSERT INTO vehicles (type, status, vehicle_condition, brand, make, model, manufacture_year, registration_number, vin, mileage, body_type, cost, engine_capacity, power, fuel_type, has_abs, transmission_type, seating_capacity, created_at, updated_at) VALUES
('BIKE', 'AVAILABLE', 'NEW', 'Yamaha', 'Yamaha', 'MT-07', 2023, 'BIKE-001', 'VINB1234567890C01', 0, 'Naked', 8000.00, 689, 74, 'PETROL', true, 'Manual', 2, NOW(), NOW()),
('BIKE', 'AVAILABLE', 'USED', 'Kawasaki', 'Kawasaki', 'Ninja 400', 2022, 'BIKE-002', 'VINB1234567890C02', 12000, 'Sport', 5500.00, 399, 49, 'PETROL', true, 'Manual', 2, NOW(), NOW()),
('BIKE', 'AVAILABLE', 'CPO', 'Honda', 'Honda', 'CBR500R', 2023, 'BIKE-003', 'VINB1234567890C03', 3000, 'Sport', 7200.00, 471, 47, 'PETROL', true, 'Manual', 2, NOW(), NOW()),
('BIKE', 'AVAILABLE', 'USED', 'Suzuki', 'Suzuki', 'SV650', 2021, 'BIKE-004', 'VINB1234567890C04', 18000, 'Naked', 7500.00, 645, 75, 'PETROL', true, 'Manual', 2, NOW(), NOW()),
('BIKE', 'AVAILABLE', 'NEW', 'Ducati', 'Ducati', 'Monster', 2023, 'BIKE-005', 'VINB1234567890C05', 0, 'Naked', 12000.00, 937, 111, 'PETROL', true, 'Manual', 2, NOW(), NOW()),
('BIKE', 'AVAILABLE', 'CPO', 'Triumph', 'Triumph', 'Street Triple', 2022, 'BIKE-006', 'VINB1234567890C06', 4000, 'Naked', 11000.00, 765, 121, 'PETROL', true, 'Manual', 2, NOW(), NOW()),
('BIKE', 'AVAILABLE', 'NEW', 'KTM', 'KTM', 'Z900', 2023, 'BIKE-007', 'VINB1234567890C07', 0, 'Naked', 9500.00, 948, 125, 'PETROL', true, 'Manual', 2, NOW(), NOW()),
('BIKE', 'AVAILABLE', 'USED', 'BMW', 'BMW', 'G310R', 2023, 'BIKE-008', 'VINB1234567890C08', 5000, 'Naked', 5000.00, 313, 34, 'PETROL', true, 'Manual', 2, NOW(), NOW()),
('BIKE', 'AVAILABLE', 'USED', 'Royal Enfield', 'Royal Enfield', 'Classic 350', 2022, 'BIKE-009', 'VINB1234567890C09', 25000, 'Cruiser', 4500.00, 349, 20, 'PETROL', true, 'Manual', 2, NOW(), NOW()),
('BIKE', 'AVAILABLE', 'USED', 'Harley Davidson', 'Harley', 'Iron 883', 2021, 'BIKE-010', 'VINB1234567890C10', 30000, 'Cruiser', 9000.00, 883, 50, 'PETROL', true, 'Manual', 2, NOW(), NOW());

-- Add Images to vehicles
INSERT INTO vehicle_images (vehicle_id, image_url) SELECT id, 'https://example.com/car_image_' || id || '.jpg' FROM vehicles WHERE type = 'CAR';
INSERT INTO vehicle_images (vehicle_id, image_url) SELECT id, 'https://example.com/bike_image_' || id || '.jpg' FROM vehicles WHERE type = 'BIKE';

-- Execute a Transaction (Alice buys CAR-001)
-- Update vehicle owner
UPDATE vehicles SET status = 'SOLD', owner_id = (SELECT id FROM vehicle_users WHERE email = 'alice@example.com') WHERE registration_number = 'CAR-001';

-- Insert transaction record
INSERT INTO vehicle_transactions (transaction_type, vehicle_id, user_id, amount, payment_mode, transaction_date) 
VALUES ('BUY', (SELECT id FROM vehicles WHERE registration_number = 'CAR-001'), (SELECT id FROM vehicle_users WHERE email = 'alice@example.com'), 25000.00, 'CREDIT_CARD', NOW());

-- Seed Reviews
INSERT INTO vehicle_reviews (vehicle_id, author_id, rating, comment, created_at, updated_at) VALUES
((SELECT id FROM vehicles WHERE registration_number = 'CAR-001'), (SELECT id FROM vehicle_users WHERE email = 'alice@example.com'), 5, 'Absolutely love my new Camry. Rides perfectly!', NOW(), NOW()),
((SELECT id FROM vehicles WHERE registration_number = 'CAR-005'), (SELECT id FROM vehicle_users WHERE email = 'bob@example.com'), 4, 'Test drove the Tesla. Acceleration is insane, but the interior feels a bit sparse.', NOW(), NOW()),
((SELECT id FROM vehicles WHERE registration_number = 'BIKE-005'), (SELECT id FROM vehicle_users WHERE email = 'alice@example.com'), 5, 'The Ducati Monster is a beast. Highly recommend!', NOW(), NOW());
