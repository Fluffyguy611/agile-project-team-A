DELIMITER $$
DROP PROCEDURE IF EXISTS Insert_Values_to_role $$
CREATE PROCEDURE Insert_Values_to_role()
BEGIN
	START TRANSACTION;

INSERT INTO `Role` (role) VALUES
('Administrator'),
('Manager'),
('Employee'),
('Supervisor'),
('Developer'),
('Designer'),
('Analyst'),
('Tester'),
('Support'),
('Intern');


END $$
DELIMITER ;
use `new_schema_AdrianN`;
CALL Insert_Values_to_role();
