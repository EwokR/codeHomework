ALTER TABLE students
    ADD CHECK ( age >= 16 ),
    ALTER COLUMN name SET NOT NULL,
    ADD CONSTRAIN unique_name UNIQUE (name),
        ALTER COLOUMN age SET DEFAULT 20;
