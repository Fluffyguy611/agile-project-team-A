package org.kainos.ea.api;

import org.kainos.ea.cli.User;
import org.kainos.ea.client.FailedToRegisterException;
import org.kainos.ea.client.InvalidEmailException;
import org.kainos.ea.client.InvalidUserException;
import org.kainos.ea.core.UserValidator;
import org.kainos.ea.db.AuthDao;
import java.sql.SQLException;

public class AuthService {
    private AuthDao authDao = new AuthDao();

    public String register(User user) throws FailedToRegisterException, InvalidEmailException, InvalidUserException {

        try {
            if(!UserValidator.isValidEmail(user.getEmail())){
                throw new InvalidEmailException();
            }

            if(!UserValidator.isValidUser(user)){
                throw new InvalidUserException();
            }

            authDao.registerUser(user);
            return user.getEmail();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToRegisterException();
        }
    }
}
