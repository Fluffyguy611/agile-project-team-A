DELIMITER $$
DROP PROCEDURE IF EXISTS drop_procedure $$
CREATE PROCEDURE drop_procedure()
BEGIN
    START TRANSACTION;
    
    DROP TABLE IF EXISTS JobRole;
    DROP TABLE IF EXISTS Band;
    DROP TABLE IF EXISTS `User`;
    DROP TABLE IF EXISTS Role;
    drop TABLE IF EXISTS Capability;
    
        COMMIT;
        SELECT 'TRANSACTION COMMITED SUCCESFULLY';

END $$
DELIMITER ;
CALL drop_procedure();