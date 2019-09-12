DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaraunts;
DELETE FROM menu_items;
DELETE FROM menus;

INSERT INTO users (id, name, email, encrypted_password) VALUES
    (100, 'Admin', 'admin@gmail.com', '$2a$10$hxsFkGIrkQ7BnaDDVHE2P.C43Diz9od9jE6UXUZpgzQo2OFaM0eMm'),
    (101, 'User', 'user@yandex.ru', '$2a$10$esmANzQePR/Mkc2yZXB.6.kd4qHdGf3B1HQ.WHcKPzqkzXqSv.BN6');

INSERT INTO user_roles (role, user_id) VALUES
    ('ROLE_ADMIN', 100),
    ('ROLE_USER', 100),
    ('ROLE_USER', 101);

INSERT INTO restaraunts (id, name) VALUES
(100, 'Weeping willow'),
(101, 'East');

INSERT INTO menus (id, restaraunt_id, date) VALUES
(100, 100, '2019-01-01'),
(101, 100, '2019-01-02'),
(102, 101, '2019-01-01'),
(103, 101, '2019-01-02');

INSERT INTO menu_items (id, menu_id, dish, price) VALUES
(100, 100, 'Meal 1 (2019-01-01, Weeping willow)', 10.99),
(102, 100, 'Meal 2 (2019-01-01, Weeping willow)', 20.01),
(103, 100, 'Meal 3 (2019-01-01, Weeping willow)', 30.00),
(104, 101, 'Meal 1 (2019-01-02, Weeping willow)', 11.99),
(105, 101, 'Meal 2 (2019-01-02, Weeping willow)', 21.01),
(106, 101, 'Meal 3 (2019-01-02, Weeping willow)', 31.00),
(107, 102, 'Meal 1 (2019-01-01, East)', 1.00);

INSERT INTO votes (id, user_id, menu_id, restaraunt_id, date) VALUES
(100, 100, 100, 100, '2019-01-01'),
(101, 101, 100, 100, '2019-01-01');
