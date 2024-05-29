CREATE TABLE IF NOT EXISTS "launcher" (
  id SERIAL PRIMARY KEY,
  launcher_id TEXT NOT NULL UNIQUE,
  type VARCHAR(25) ,
  registration_date TIMESTAMP
);