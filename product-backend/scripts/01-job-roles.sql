DELIMITER $$
DROP PROCEDURE IF EXISTS Create_role_table $$
CREATE PROCEDURE Create_role_table()
BEGIN
	START TRANSACTION;    

CREATE TABLE IF NOT EXISTS JobRole (
	Id int AUTO_INCREMENT primary key, 
    Name VARCHAR(64) NOT NULL unique,
    Description VARCHAR(3000) NOT NULL,
    SharePointLink VARCHAR(2137) NOT NULL
);

END $$
DELIMITER ;
CALL Create_role_table();
