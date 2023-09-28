package org.kainos.ea.service;

import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.FailedToRegisterException;
import org.kainos.ea.exception.InvalidEmailException;
import org.kainos.ea.exception.InvalidPasswordException;
import org.kainos.ea.exception.InvalidRoleIdException;
import org.kainos.ea.model.User;

import java.sql.SQLException;

public class AuthService {
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

            authDao.registerUser(user, databaseConnector.getConnection());
            return user.getEmail();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToRegisterException();
        }
    }
}
