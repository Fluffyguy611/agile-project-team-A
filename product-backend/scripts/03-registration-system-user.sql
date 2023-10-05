DELIMITER $$
DROP PROCEDURE IF EXISTS Create_registration_tables $$
CREATE PROCEDURE Create_registration_tables()
BEGIN
	START TRANSACTION;

CREATE TABLE User (
	Id INT NOT NULL auto_increment,
	Email VARCHAR(64) NOT NULL UNIQUE,
    Password VARCHAR(64) NOT NULL,
    RoleId TINYINT NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (RoleId) REFERENCES Role(Id)
);

END $$
DELIMITER ;
CALL Create_registration_tables();