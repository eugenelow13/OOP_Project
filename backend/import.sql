USE mydatabase;

DELETE FROM ticket;
DELETE FROM booking;
DELETE FROM booking_seq;
DELETE FROM event;
DELETE FROM event_seq;
DELETE FROM user_roles;
DELETE FROM roles;
DELETE FROM customer;
DELETE FROM event_manager;
DELETE FROM ticket_seq;
DELETE FROM user_seq;
DELETE FROM user;

INSERT INTO booking_seq VALUES (1);

-- INSERT INTO event (id, cancellation_fee, customer_attendance, date, image_url, event_status, description, name, ticket_price, tickets_available, type, venue)
--   VALUES
--   (1, 20, 0, "2024-11-22 20:00:00", NULL, "PLANNED", NULL, "Singapore Open", 50, 5000, "Sports", "Sports Hub"),
--   (2, 10, 0, "2024-07-15 18:00:00", NULL, "PLANNED", NULL, "Music Festival", 80, 10000, "Music", "Outdoor Stadium"),
--   (3, 30, 0, "2025-03-10 09:00:00", NULL, "PLANNED", NULL, "Tech Conference", 200, 500, "Technology", "Convention Center"),
--   (4, 5, 0, "2024-05-01 10:00:00", NULL, "PLANNED", NULL, "Food Expo", 30, 2000, "Food & Beverage", "Exhibition Hall");

  INSERT INTO event (id, cancellation_fee, customer_attendance, date, image_url, event_status, description, name, ticket_price, tickets_available, type, venue)
  VALUES
  (1, 20, 0, "2024-11-22 20:00:00", "https://www.sportshub.com.sg/sites/default/files/2023-06/Event%20Hero%20Banner%201200-675%20%E2%94%90%E2%95%9C%E2%96%92%E2%94%A4_1.jpg", "PLANNED", "The 'Taylor Swift Eras Tour' is a concert series featuring Taylor Swift, where she performs a selection of songs representing the different eras of her career.", "Taylor Swift: The Eras Tour", 50, 5000, "Concert", "National Stadium"),
  (2, 10, 0, "2024-07-15 18:00:00", "https://www.sportshub.com.sg/sites/default/files/2024-02/1200x675.png", "PLANNED", "The FIFA World Cup qualifiers match between Singapore and China promises a thrilling clash as both teams vie for a crucial victory to advance in the tournament.", "FIFA World Cup Qualifiers 2026: Singapore v.s. China", 80, 10000, "Sports", "Sports Hub"),
  (3, 30, 0, "2025-03-10 09:00:00","https://www.sportshub.com.sg/sites/default/files/2024-01/SH2-BrunoMars-Event%20Hero%20Banner_0.jpg", "PLANNED", "Experience the electrifying energy of Bruno Mars live in Singapore as he performs his chart-topping hits with unmatched charisma and flair, promising an unforgettable night filled with soulful melodies and infectious rhythms", "Bruno Mars: LIVE in Singapore", 200, 500, "Concert", "National Stadium"),
  (4, 5, 0, "2024-05-01 10:00:00", "https://www.sportshub.com.sg/sites/default/files/2023-11/Event%20Hero%20Banner%201200x675pxKeyArt.jpg", "PLANNED", "Embark on a magical journey through Disney's enchanting universe with beloved characters from classic tales.", " Disney on ICE: 100 Years of Wonder", 30, 2000, "Theatre", "Indoor Stadium");

INSERT INTO event_seq VALUES (101);

INSERT INTO user (user_id, email, full_name, password)
VALUES
  (1, "john.doe@example.com", "John Doe", '$2a$10$XDpuW6oAa01P3Uy28qr11eNu1EXKh73czJ/2Bt8YX6KX8S9IfmJSq'),
  (2, "jane.doe@example.com", "Jane Doe", '$2a$10$XDpuW6oAa01P3Uy28qr11eNu1EXKh73czJ/2Bt8YX6KX8S9IfmJSq'),
  (3, "janejohn.doe@example.com", "JaneJohn Doe", '$2a$10$XDpuW6oAa01P3Uy28qr11eNu1EXKh73czJ/2Bt8YX6KX8S9IfmJSq');

INSERT INTO customer (user_id, credit_balance) VALUES (1, 1000);

INSERT INTO event_manager VALUES (2);

INSERT INTO roles
  VALUES
  (1, "Default customer role", "CUSTOMER"),
  (2, "Ticketing officer role", "TICKETING_OFFICER"),
  (3, "Event manager role", "EVENT_MANAGER");

INSERT INTO ticket_seq VALUES (1);

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1), (2, 3), (3, 2);

INSERT INTO user_seq VALUES (101);
