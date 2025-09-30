-- Add unique constraint on email
ALTER TABLE users
    ADD CONSTRAINT UK_users_email UNIQUE (email);

-- Add check constraint for name length
ALTER TABLE users
    ADD CONSTRAINT CK_users_name_length
        CHECK (CHAR_LENGTH(TRIM(name)) >= 2);

-- Add check constraint for last_name length (opcional, lo puedes quitar si quieres permitir nulos o vacíos)
ALTER TABLE users
    ADD CONSTRAINT CK_users_last_name_length
        CHECK (last_name IS NULL OR CHAR_LENGTH(TRIM(last_name)) >= 2);
