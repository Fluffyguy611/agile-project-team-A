package org.kainos.ea.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.User;
import org.mindrot.jbcrypt.BCrypt;
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

    public AuthService(DatabaseConnector databaseConnector, AuthDao authDao, AuthValidator authValidator) {
        this.databaseConnector = databaseConnector;
        this.authDao = authDao;
        this.authValidator = authValidator;
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

            user.setPassword(hashPassword(user.getPassword()));
            authDao.registerUser(user, databaseConnector.getConnection());
        } catch (SQLException | DatabaseConnectionException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());

            throw new FailedToRegisterException();
        }
    }

    public String login(User user) throws FailedToLoginException, DatabaseConnectionException, UserDoesNotExistException, InvalidPasswordException {
        try {
            Optional<User> existingUser = authDao.getUserByEmail(user.getEmail(), databaseConnector.getConnection());

            if (existingUser.isEmpty()) {
                throw new UserDoesNotExistException();
            }

            if (!verifyHash(user.getPassword(), existingUser.get().getPassword())) {
                throw new InvalidPasswordException();
            }

            return generateToken(user);
        } catch (SQLException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());

            throw new FailedToLoginException();
        }
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public String generateToken(User user) throws SQLException {
        Algorithm algorithm = Algorithm.HMAC256("erggv45wv54c53xd345vcg4v54yv2");
        Date expiry = DateUtils.addHours(new Date(), 1);

        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRoleId())
                .withExpiresAt(expiry)
                .sign(algorithm);

        return token;
    }
}
