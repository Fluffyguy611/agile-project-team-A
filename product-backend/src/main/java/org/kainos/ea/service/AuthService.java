package org.kainos.ea.service;

import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AuthService {
    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final DatabaseConnector databaseConnector;
    private final AuthDao authDao;
    private final AuthValidator authValidator;

    public AuthService(DatabaseConnector databaseConnector, AuthDao authDao, AuthValidator authValidator) {
        this.databaseConnector = databaseConnector;
        this.authDao = authDao;
        this.authValidator = authValidator;
    }

    public String register(User user) throws FailedToRegisterException, InvalidEmailException, InvalidPasswordException, InvalidRoleIdException {
        try {
            if (!authValidator.isValidEmail(user.getEmail())) {
                throw new InvalidEmailException();
            }

            if (!authValidator.isValidPassword(user.getPassword())) {
                throw new InvalidPasswordException();
            }

            if (!authValidator.isRoleIdValid(user.getRoleId())) {
                throw new InvalidRoleIdException();
            }

            user.setPassword(hashPassword(user.getPassword()));
            authDao.registerUser(user, databaseConnector.getConnection());

            return user.getEmail();
        } catch (SQLException | DatabaseConnectionException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());

            throw new FailedToRegisterException();
        }
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
