DELIMITER $$
DROP PROCEDURE IF EXISTS Create_registration_tables $$
CREATE PROCEDURE Create_registration_tables()
BEGIN
	START TRANSACTION;

CREATE TABLE Role (
	Id TINYINT NOT NULL,
    Name VARCHAR(64) NOT NULL,
    PRIMARY KEY (Id)
);

CREATE TABLE User (
	Id INT NOT NULL auto_increment,
	Email VARCHAR(64) NOT NULL UNIQUE,
    Password VARCHAR(64) NOT NULL,
    RoleId TINYINT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (RoleId) REFERENCES Roles(Id)
);

END $$
DELIMITER ;
CALL Create_registration_tables();