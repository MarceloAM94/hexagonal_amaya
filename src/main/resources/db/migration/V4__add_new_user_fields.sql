ALTER TABLE users
    ADD COLUMN mother_last_name VARCHAR(100),
    ADD COLUMN age INT,
    ADD COLUMN dni VARCHAR(20) UNIQUE,
    ADD COLUMN phone_number VARCHAR(20);
