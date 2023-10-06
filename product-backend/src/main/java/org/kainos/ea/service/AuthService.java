package org.kainos.ea.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;


public class AuthService {
    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final DatabaseConnector databaseConnector;
    private final AuthDao authDao;
    private final AuthValidator authValidator;
    private final PasswordService passwordService;

    public AuthService(DatabaseConnector databaseConnector, AuthDao authDao, AuthValidator authValidator, PasswordService passwordService) {
        this.databaseConnector = databaseConnector;
        this.authDao = authDao;
        this.authValidator = authValidator;
        this.passwordService = passwordService;
    }

    public void register(User user) throws FailedToRegisterException, InvalidEmailException, InvalidPasswordException, InvalidRoleIdException {
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

            user.setPassword(passwordService.hashPassword(user.getPassword()));
            authDao.registerUser(user, databaseConnector.getConnection());
        } catch (SQLException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());

            throw new FailedToRegisterException();
        }
    }

    public String login(User user) throws FailedToLoginException, UserDoesNotExistException, InvalidPasswordException {
        try {
            Optional<User> existingUser = authDao.getUserByEmail(user.getEmail(), databaseConnector.getConnection());

            if (existingUser.isEmpty()) {
                throw new UserDoesNotExistException();
            }

            if (!passwordService.verifyHash(user.getPassword(), existingUser.get().getPassword())) {
                throw new InvalidPasswordException();
            }

            return generateToken(existingUser);
        } catch (SQLException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());

            throw new FailedToLoginException();
        }
    }


    public String generateToken(Optional<User> user) throws SQLException {
        Algorithm algorithm = Algorithm.HMAC256("erggv45wv54c53xd345vcg4v54yv2");
        Date expiry = DateUtils.addHours(new Date(), 1);

        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim("email", user.get().getEmail())
                .withClaim("role", user.get().getRoleId())
                .withExpiresAt(expiry)
                .sign(algorithm);

        return token;
    }
}
