CREATE TABLE users
(
    ID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    passwrd VARCHAR(100) NOT NULL,
    realname VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE playlists
(
    ID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (user_id) REFERENCES users (ID)
);

CREATE TABLE files
(
    ID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    file_type VARCHAR(10) NOT NULL,
    file_path VARCHAR(100) NOT NULL,
    user_id INT NOT NULL,
    playlist_id INT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (user_id) REFERENCES users (ID),
    FOREIGN KEY (playlist_id) REFERENCES playlists (ID)
);

CREATE TABLE device
(
    ID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    user_id INT NOT NULL,
    playlist_id INT,
    PRIMARY KEY (ID),
    FOREIGN KEY (user_id) REFERENCES users (ID),
    FOREIGN KEY (playlist_id) REFERENCES playlists (ID)
);

CREATE TABLE dashboard_data
(
    ID INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    value1 DECIMAL,
    value2 DECIMAL,
    value3 DECIMAL,
    value4 DECIMAL,
    value5 DECIMAL,
    PRIMARY KEY (ID),
    FOREIGN KEY (user_id) REFERENCES users (ID)
);