
DROP DATABASE GladiatorManager;
CREATE DATABASE GladiatorManager;
USE GladiatorManager;

CREATE USER GMS IDENTIFIED BY 'password'; 

GRANT USAGE ON *.* TO 'GMS'@'%' IDENTIFIED BY 'password'; 
GRANT ALL PRIVILEGES ON GladiatorManager.* TO 'GMS'@'%'; 

DROP TABLE Accounts;

CREATE TABLE Accounts (id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    team_name VARCHAR(50) UNIQUE,
    state INT NOT NULL,
    auth_token VARCHAR(50),
    pw_reset_token VARCHAR(50),
    PRIMARY KEY (ID));
    
DROP TABLE Mercenaries;

CREATE TABLE Mercenaries (id INT NOT NULL AUTO_INCREMENT, 
    account_id INT,
    name VARCHAR(50) NOT NULL,
    level INT NOT NULL,
    xp INT NOT NULL,
    age INT NOT NULL,
    strength INT NOT NULL,
    agility INT NOT NULL,
    constitution INT NOT NULL,
    intelligence INT NOT NULL,
    willpower INT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (account_id) REFERENCES Accounts(id));
