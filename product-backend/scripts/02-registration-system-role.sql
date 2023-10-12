DELIMITER $$
DROP PROCEDURE IF EXISTS Create_registration_tables $$
CREATE PROCEDURE Create_registration_tables()
BEGIN
	START TRANSACTION;

CREATE TABLE IF NOT EXISTS Role (
	Id TINYINT NOT NULL,
    Name VARCHAR(64) NOT NULL,
    PRIMARY KEY (Id)
);

END $$
DELIMITER ;
CALL Create_registration_tables();