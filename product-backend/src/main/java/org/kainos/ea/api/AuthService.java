package org.kainos.ea.api;

import org.kainos.ea.cli.User;
import org.kainos.ea.core.UserValidator;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.exception.FailedToRegisterException;
import org.kainos.ea.exception.InvalidEmailException;
import org.kainos.ea.exception.InvalidPasswordException;

import java.sql.SQLException;

public class AuthService {
    private AuthDao authDao = new AuthDao();

    public String register(User user) throws FailedToRegisterException, InvalidEmailException, InvalidPasswordException {

        try {
            if (!UserValidator.isValidEmail(user.getEmail())) {
                throw new InvalidEmailException();
            }

            if (!UserValidator.isValidPassword(user.getPassword())) {
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
