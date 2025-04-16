DELIMITER $$

CREATE TRIGGER prevent_superadmin_demotion
BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
    -- Prevent changing isAdmin for the super admin
    IF OLD.UserID = 1 AND OLD.IsAdmin = 1 AND NEW.IsAdmin <> 1 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Cannot change isAdmin status of super admin.';
    END IF;
END$$

DELIMITER ;
