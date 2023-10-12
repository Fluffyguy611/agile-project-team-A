DELIMITER $$
DROP PROCEDURE IF EXISTS Create_band_table $$
CREATE PROCEDURE Create_band_table()
BEGIN
	START TRANSACTION;

CREATE TABLE IF NOT EXISTS Band (
	Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Name VARCHAR(64) NOT NULL,
	`Level` TINYINT NOT NULL
);

END $$
DELIMITER ;
CALL Create_band_table();