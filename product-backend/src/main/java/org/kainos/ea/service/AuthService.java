package org.kainos.ea.service;

import org.kainos.ea.db.AuthDao;
import org.kainos.ea.exception.FailedToRegisterException;
import org.kainos.ea.exception.InvalidEmailException;
import org.kainos.ea.exception.InvalidPasswordException;
import org.kainos.ea.model.User;

import java.sql.SQLException;

public class AuthService {
    private AuthDao authDao;
    private AuthValidator authValidator;

    public AuthService(AuthDao authDao, AuthValidator authValidator) {
        this.authDao = authDao;
        this.authValidator = authValidator;
    }

    public String register(User user) throws FailedToRegisterException, InvalidEmailException, InvalidPasswordException {

        try {
            if (!authValidator.isValidEmail(user.getEmail())) {
                throw new InvalidEmailException();
            }

            if (!authValidator.isValidPassword(user.getPassword())) {
                throw new InvalidPasswordException();
            }

            authDao.registerUser(user);
            return user.getEmail();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToRegisterException();
        }
    }
}
