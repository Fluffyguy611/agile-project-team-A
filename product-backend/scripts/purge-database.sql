DELIMITER $$
DROP PROCEDURE IF EXISTS drop_procedure $$
CREATE PROCEDURE drop_procedure()
BEGIN
    START TRANSACTION;
    
    DROP TABLE IF EXISTS JobRole;
    DROP TABLE IF EXISTS Role;
    DROP TABLE IF EXISTS `User`;
    
    
        COMMIT;
        SELECT 'TRANSACTION COMMITED SUCCESFULLY';

END $$
DELIMITER ;
CALL drop_procedure();