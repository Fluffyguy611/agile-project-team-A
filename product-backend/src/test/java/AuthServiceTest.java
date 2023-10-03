import org.junit.jupiter.api.Test;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.User;
import org.kainos.ea.service.AuthService;
import org.kainos.ea.service.AuthValidator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    DatabaseConnector databaseConnectorMock = mock(DatabaseConnector.class);
    AuthDao authDaoMock = mock(AuthDao.class);
    AuthValidator authValidatorMock = mock(AuthValidator.class);
    AuthService authService = new AuthService(databaseConnectorMock, authDaoMock, authValidatorMock);

    @Test
    void registerUser_When_UserIsValid_Expect_EmailReturned() throws DatabaseConnectionException, SQLException, InvalidPasswordException, InvalidRoleIdException, InvalidEmailException, FailedToRegisterException {
        User mockedUser = new User("username@kainos.com", "strongPassword123!", 1);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(true);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(true);
        when(authValidatorMock.isRoleIdValid(mockedUser.getRoleId())).thenReturn(true);
        when(authDaoMock.registerUser(mockedUser, databaseConnectorMock.getConnection())).thenReturn(mockedUser.getEmail());

        String returnedEmail = authService.register(mockedUser);

        assertThat(returnedEmail).isEqualTo(mockedUser.getEmail());
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
    void registerUser_When_ThereIsDatabaseError_Expect_FailedToRegisterExceptionToBeThrown() throws SQLException, DatabaseConnectionException {
        User mockedUser = new User("username@kainos.com", "Gig@Passw0rD", 1);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(true);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(true);
        when(authValidatorMock.isRoleIdValid(mockedUser.getRoleId())).thenReturn(true);
        when(authDaoMock.registerUser(mockedUser, databaseConnectorMock.getConnection())).thenThrow(new SQLException());

        assertThatExceptionOfType(FailedToRegisterException.class)
                .isThrownBy(() -> authService.register(mockedUser))
                .withMessageMatching("Failed to register user");
    }
}