DELIMITER $$
DROP PROCEDURE IF EXISTS Create_capability_table $$
CREATE PROCEDURE Create_capability_table()
BEGIN
	START TRANSACTION;

CREATE TABLE IF NOT EXISTS Capability (
Id int AUTO_INCREMENT primary key,
Capability VARCHAR (50) NOT NULL,
Name VARCHAR(64) NOT NULL unique,
Photo LONGTEXT NOT NULL,
Message TEXT NOT NULL
);

ALTER TABLE JobRole
ADD CapabilityId int NOT NULL,
ADD CONSTRAINT fk_CapabilityId
FOREIGN KEY (CapabilityId)
REFERENCES Capability(Id);

END $$
DELIMITER ;
CALL Create_capability_table();