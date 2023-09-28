import org.junit.jupiter.api.Test;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.exception.FailedToRegisterException;
import org.kainos.ea.exception.InvalidEmailException;
import org.kainos.ea.exception.InvalidPasswordException;
import org.kainos.ea.model.User;
import org.kainos.ea.service.AuthService;
import org.kainos.ea.service.AuthValidator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    AuthDao authDaoMock = mock(AuthDao.class);
    AuthValidator authValidatorMock = mock(AuthValidator.class);
    AuthService authService = new AuthService(authDaoMock, authValidatorMock);

    @Test
    void registerUser_When_ThereIsValidationError_Expect_InvalidEmailExceptionToBeThrown() {
        User mockedUser = new User("invalidemail", "strongPassword123!", 1);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(false);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(true);

        assertThatExceptionOfType(InvalidEmailException.class)
                .isThrownBy(() -> authService.register(mockedUser))
                .withMessage("Provided email is invalid");
    }

    @Test
    void registerUser_When_ThereIsValidationError_Expect_InvalidPasswordExceptionToBeThrown() {
        User mockedUser = new User("username@kainos.com", "weekpass", 1);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(true);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(false);

        assertThatExceptionOfType(InvalidPasswordException.class)
                .isThrownBy(() -> authService.register(mockedUser))
                .withMessage("Provided password is invalid");
    }

    @Test
    void registerUser_When_ThereIsDatabaseError_Expect_FailedToRegisterExceptionToBeThrown() throws SQLException, FailedToRegisterException {
        User mockedUser = new User("username@kainos.com", "Gig@Passw0rD", 1);

        when(authValidatorMock.isValidEmail(mockedUser.getEmail())).thenReturn(true);
        when(authValidatorMock.isValidPassword(mockedUser.getPassword())).thenReturn(true);
        when(authDaoMock.registerUser(mockedUser)).thenThrow(new SQLException());

        assertThatExceptionOfType(FailedToRegisterException.class)
                .isThrownBy(() -> authService.register(mockedUser))
                .withMessageMatching("Failed to register user");
    }
}
