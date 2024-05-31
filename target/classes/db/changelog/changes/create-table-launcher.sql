CREATE TABLE IF NOT EXISTS "launcher" (
  id SERIAL PRIMARY KEY,
  launcher_code TEXT NOT NULL UNIQUE,
  type VARCHAR(25) ,
  registration_date TIMESTAMP
);