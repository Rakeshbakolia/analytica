CREATE TABLE IF NOT EXISTS "satellite" (
  id SERIAL PRIMARY KEY,
  satellite_id TEXT NOT NULL UNIQUE,
  country VARCHAR(25) NOT NULL,
  launch_date TIMESTAMP,
  mass NUMERIC,
  launcher_id BIGINT NOT NULL,
  constraint fk_launcher
       foreign key (launcher_id)
       REFERENCES launcher (id)
);