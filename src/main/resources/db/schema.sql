DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE restaraunts IF EXISTS;
DROP TABLE menu_items IF EXISTS;
DROP TABLE menu IF EXISTS;

CREATE TABLE users
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY,
    name          VARCHAR(255)            NOT NULL,
    email         VARCHAR(255)            NOT NULL,
    password      VARCHAR(255)            NOT NULL,
    registered    TIMESTAMP DEFAULT now() NOT NULL,
    enabled       BOOLEAN DEFAULT TRUE    NOT NULL,
    CONSTRAINT user_email_idx UNIQUE (email)
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaraunts
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY,
    name          VARCHAR(255)            NOT NULL,
    active        BOOLEAN DEFAULT TRUE    NOT NULL,
    CONSTRAINT restaraunt_name_idx UNIQUE (name)
);

CREATE TABLE menus
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 100) PRIMARY KEY,
    restaraunt_id INTEGER   NOT NULL,
    date          TIMESTAMP NOT NULL,
    CONSTRAINT menu_restaraunt_date_idx UNIQUE (restaraunt_id, date),
    FOREIGN KEY (restaraunt_id) REFERENCES restaraunts (id) ON DELETE CASCADE
);

CREATE TABLE menu_items
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 100) PRIMARY KEY,
    menu_id       INTEGER   NOT NULL,
    dish          VARCHAR(255)            NOT NULL,
    price         DECIMAL(12, 2),
    CONSTRAINT menu_dish_idx UNIQUE (menu_id, dish),
    FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);
