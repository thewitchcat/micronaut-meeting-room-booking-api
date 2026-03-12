DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS rooms;

CREATE TABLE users (
  id UUID PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  email VARCHAR(320) UNIQUE NOT NULL,

  CONSTRAINT check_users_name_length CHECK (char_length(name) >= 3),
  CONSTRAINT check_users_email_length CHECK (char_length(email) >= 5)
);

CREATE TABLE rooms (
  id UUID PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  active BOOLEAN NOT NULL,
  capacity SMALLINT NOT NULL,
  created_at TIMESTAMP DEFAULT LOCALTIMESTAMP NOT NULL,

  CONSTRAINT check_rooms_name_length CHECK (char_length(name) >= 3),
  CONSTRAINT check_rooms_capacity CHECK (capacity > 0)
);

CREATE TABLE bookings (
  id UUID PRIMARY KEY,
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL,
  status VARCHAR(10) CHECK (status IN ('CONFIRMED', 'CANCELLED')) NOT NULL,
  user_id UUID NOT NULL,
  room_id UUID NOT NULL,
  created_at TIMESTAMP DEFAULT LOCALTIMESTAMP NOT NULL,

  CONSTRAINT check_booking_time CHECK (end_time > start_time),

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (room_id) REFERENCES rooms (id)
);