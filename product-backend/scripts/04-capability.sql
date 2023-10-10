DELIMITER $$
DROP PROCEDURE IF EXISTS Create_capability_table $$
CREATE PROCEDURE Create_capability_table()
BEGIN
	START TRANSACTION;

CREATE TABLE IF NOT EXISTS Capability (
Id int AUTO_INCREMENT primary key,
Capability VARCHAR (50) NOT NULL,
Name VARCHAR(64) NOT NULL unique,
Photo BLOB NOT NULL,
Message TEXT NOT NULL
);

END $$
DELIMITER ;
CALL Create_capability_table();