package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.db.BandDao;
import org.kainos.ea.exception.BandAlreadyExistsException;
import org.kainos.ea.exception.FailedToCreateNewBandException;
import org.kainos.ea.exception.InvalidJobBandException;
import org.kainos.ea.exception.JobBandAlreadyExistsException;
import org.kainos.ea.model.Band;
import org.kainos.ea.model.BandRequest;
import org.kainos.ea.service.BandService;
import org.kainos.ea.service.BandValidator;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BandServiceTests {
    private final BandDao bandDaoMock = mock(BandDao.class);
    private final BandValidator bandValidatorMock = mock(BandValidator.class);
    private final BandService bandService = new BandService(bandDaoMock, bandValidatorMock);
    private final Band mockedBandInstance = new Band(100, "New job Band", 5);

    @Test
    public void createNewJobBandSuccess() throws SQLException, FailedToCreateNewBandException, BandAlreadyExistsException {
        BandRequest bandRequest = new BandRequest("Test Band", 6);
        when(bandDaoMock.createNewBand(bandRequest)).thenReturn(Optional.of(mockedBandInstance));

        Band newBand = bandService.createNewBand(bandRequest);

        verify(bandDaoMock).createNewBand(bandRequest);
        assertEquals(newBand, mockedBandInstance);
    }

    @Test
    public void createNewJobBandFailure() throws FailedToCreateNewBandException, SQLException {
        BandRequest bandRequest = new BandRequest("Test Band", 6);
        when(bandDaoMock.createNewBand(bandRequest)).thenReturn(Optional.empty());

        assertThatExceptionOfType(FailedToCreateNewBandException.class)
                .isThrownBy(() -> bandService.createNewBand(bandRequest));
    }

    @Test
    public void createNewJobBandFailsWhenInputIsIncorrect() throws SQLException, FailedToCreateNewBandException, InvalidJobBandException, JobBandAlreadyExistsException, BandAlreadyExistsException {
        BandRequest mockedBandRequest = new BandRequest("Test Band", 8);
        when(bandDaoMock.createNewBand(mockedBandRequest)).thenReturn(Optional.of(mockedBandInstance));

        Band newBand = bandService.createNewBand(mockedBandRequest);

        assertThat(newBand).isEqualTo(mockedBandInstance);
    }

    @Test
    public void createNewJobBand_When_BandAlreadyExists_Then_Expect_BandAlreadyExistsExceptionToBeThrown() throws SQLException, FailedToCreateNewBandException, InvalidJobBandException, JobBandAlreadyExistsException, BandAlreadyExistsException {
        BandRequest mockedBandRequest = new BandRequest("MockedName", 6);
        when(bandDaoMock.createNewBand(mockedBandRequest)).thenReturn(Optional.of(mockedBandInstance));
        when(bandDaoMock.getBandByName("MockedName")).thenReturn(Optional.of(new Band(1, "", 0)));

        assertThatExceptionOfType(BandAlreadyExistsException.class)
                .isThrownBy(() -> bandService.createNewBand(mockedBandRequest));
    }
}
