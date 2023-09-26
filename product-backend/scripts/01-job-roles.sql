DELIMITER $$
DROP PROCEDURE IF EXISTS Create_role_table $$
CREATE PROCEDURE Create_role_table()
BEGIN
	START TRANSACTION;    

CREATE TABLE IF NOT EXISTS `Role` (
	id int AUTO_INCREMENT primary key, 
    role VARCHAR(64) NOT NULL unique
);

END $$
DELIMITER ;
use `new_schema_AdrianN`;
CALL Create_role_table();