DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaraunts;

INSERT INTO users (id, name, email, password) VALUES
    (100, 'Admin', 'admin@gmail.com', 'admin'),
    (101, 'User', 'user@yandex.ru', 'password');

INSERT INTO user_roles (role, user_id) VALUES
    ('ROLE_ADMIN', 100),
    ('ROLE_USER', 100),
    ('ROLE_USER', 101);

INSERT INTO restaraunts (id, name) VALUES
(100, 'Weeping willow'),
(101, 'East');
