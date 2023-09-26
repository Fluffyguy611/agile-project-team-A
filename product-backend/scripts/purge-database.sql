DELIMITER $$
DROP PROCEDURE IF EXISTS drop_procedure $$
CREATE PROCEDURE drop_procedure()
BEGIN
    START TRANSACTION;
    
    DROP TABLE IF EXISTS `Role`;
    
    
        COMMIT;
        SELECT 'TRANSACTION COMMITED SUCCESFULLY';

END $$
DELIMITER ;
use `new_schema_AdrianN`;
CALL drop_procedure();