CREATE TABLE IF NOT EXISTS users (
  id uuid NOT NULL,
  name varchar(255) NOT NULL,
  email varchar(255) NOT NULL UNIQUE,
  password text NOT NULL,

  CONSTRAINT users_pkey PRIMARY KEY (id)
);