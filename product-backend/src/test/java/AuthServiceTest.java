import org.junit.jupiter.api.Test;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.User;
import org.kainos.ea.service.AuthService;
import org.kainos.ea.service.AuthValidator;
import org.kainos.ea.service.PasswordService;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    DatabaseConnector databaseConnectorMock = mock(DatabaseConnector.class);
    AuthDao authDaoMock = mock(AuthDao.class);
    AuthValidator authValidatorMock = mock(AuthValidator.class);
    PasswordService passwordServiceMock = mock(PasswordService.class);
    AuthService authService = new AuthService(databaseConnectorMock, authDaoMock, authValidatorMock, passwordServiceMock);

    @Test
    void registerUser_When_UserIsValid_Expect_EmailReturned() throws SQLException, InvalidPasswordException, InvalidRoleIdException, InvalidEmailException, FailedToRegisterException {
        User mockedUser = new User("username@kainos.com", "strongPassword123!", 1);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(true);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(true);
        when(authValidatorMock.isRoleIdValid(mockedUser.getRoleId())).thenReturn(true);
        when(authDaoMock.registerUser(mockedUser, databaseConnectorMock.getConnection())).thenReturn(mockedUser.getEmail());

        authService.register(mockedUser);

        verify(authDaoMock).registerUser(mockedUser, databaseConnectorMock.getConnection());
    }

    @Test
    void registerUser_When_ThereIsValidationError_Expect_InvalidEmailExceptionToBeThrown() {
        User mockedUser = new User("invalid_email", "strongPassword123!", 1);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(false);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(true);
        when(authValidatorMock.isRoleIdValid(mockedUser.getRoleId())).thenReturn(true);

        assertThatExceptionOfType(InvalidEmailException.class)
                .isThrownBy(() -> authService.register(mockedUser))
                .withMessage("Provided email is invalid");
    }

    @Test
    void registerUser_When_ThereIsValidationError_Expect_InvalidPasswordExceptionToBeThrown() {
        User mockedUser = new User("username@kainos.com", "weak_password", 1);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(true);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(false);
        when(authValidatorMock.isRoleIdValid(mockedUser.getRoleId())).thenReturn(true);

        assertThatExceptionOfType(InvalidPasswordException.class)
                .isThrownBy(() -> authService.register(mockedUser))
                .withMessage("Provided password is invalid");
    }

    @Test
    void registerUser_When_ThereIsValidationError_Expect_InvalidRoleIdExceptionToBeThrown() {
        User mockedUser = new User("username@kainos.com", "strongPassword123!", 3);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(true);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(true);
        when(authValidatorMock.isRoleIdValid(mockedUser.getRoleId())).thenReturn(false);

        assertThatExceptionOfType(InvalidRoleIdException.class)
                .isThrownBy(() -> authService.register(mockedUser))
                .withMessage("Provided role id is invalid");
    }

    @Test
    void registerUser_When_ThereIsDatabaseError_Expect_FailedToRegisterExceptionToBeThrown() throws SQLException {
        User mockedUser = new User("username@kainos.com", "Gig@Passw0rD", 1);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(true);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(true);
        when(authValidatorMock.isRoleIdValid(mockedUser.getRoleId())).thenReturn(true);
        when(authDaoMock.registerUser(mockedUser, databaseConnectorMock.getConnection())).thenThrow(new SQLException());

        assertThatExceptionOfType(FailedToRegisterException.class)
                .isThrownBy(() -> authService.register(mockedUser))
                .withMessageMatching("Failed to register user");
    }

    @Test
    void loginUser_When_UserIsValid_Expect_TokenReturned() throws SQLException, InvalidPasswordException, UserDoesNotExistException, FailedToLoginException {
        User mockedUser = new User("username@kainos.com", "strongPassword123!", 1);
        Optional<User> existingUserMock = Optional.of(new User("username@kainos.com", "strongPassword123!", 1));

        when(passwordServiceMock.verifyHash(mockedUser.getPassword(), existingUserMock.get().getPassword())).thenReturn(true);
        when(authDaoMock.getUserByEmail(mockedUser.getEmail(), databaseConnectorMock.getConnection())).thenReturn(existingUserMock);

        String token = authService.login(mockedUser);

        assertThat(token).isNotEmpty();
    }

    @Test
    void loginUser_When_ThereIsWrongPasswordError_Expect_InvalidPasswordException() throws SQLException {
        User mockedUser = new User("username@kainos.com", "invalid_password", 1);
        Optional<User> existingUserMock = Optional.of(new User("username@kainos.com", "strongPassword123!", 1));

        when(passwordServiceMock.verifyHash(mockedUser.getPassword(), existingUserMock.get().getPassword())).thenReturn(false);
        when(authDaoMock.getUserByEmail(mockedUser.getEmail(), databaseConnectorMock.getConnection())).thenReturn(existingUserMock);

        assertThatExceptionOfType(InvalidPasswordException.class)
                .isThrownBy(() -> authService.login(mockedUser))
                .withMessage("Provided password is invalid");
    }

    @Test
    void loginUser_When_ThereIsWrongPasswordError_Expect_FailedToLoginException() throws SQLException {
        User mockedUser = new User("username@kainos.com", "strongPassword123!", 1);
        Optional<User> existingUserMock = Optional.of(new User("username@kainos.com", "strongPassword123!", 1));

        when(passwordServiceMock.verifyHash(mockedUser.getPassword(), existingUserMock.get().getPassword())).thenReturn(true);
        when(authDaoMock.getUserByEmail(mockedUser.getEmail(), databaseConnectorMock.getConnection())).thenThrow(new SQLException());

        assertThatExceptionOfType(FailedToLoginException.class)
                .isThrownBy(() -> authService.login(mockedUser))
                .withMessageMatching("Failed to login");
    }

    @Test
    void loginUser_When_UserDoesNotExistError_Expect_UserDoesNotExistException() throws SQLException {
        User mockedUser = new User("username@kainos.com", "strongPassword123!", 1);

        when(authDaoMock.getUserByEmail(mockedUser.getEmail(), databaseConnectorMock.getConnection())).thenReturn(Optional.empty());

        assertThatExceptionOfType(UserDoesNotExistException.class)
                .isThrownBy(() -> authService.login(mockedUser))
                .withMessageMatching("User does not exist");
    }
}
