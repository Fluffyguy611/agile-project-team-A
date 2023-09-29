package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.exception.FailedToCreateNewJobRoleException;
import org.kainos.ea.exception.InvalidJobRoleException;
import org.kainos.ea.exception.JobRoleAlreadyExistsException;
import org.kainos.ea.model.JobRole;
import org.kainos.ea.model.JobRoleRequest;
import org.kainos.ea.service.JobRoleService;
import org.kainos.ea.service.JobRoleValidator;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class JobRoleServiceTests {

    private final JobRoleDao jobRoleDaoMock = mock(JobRoleDao.class);
    private final JobRoleValidator jobRoleValidatorMock = mock(JobRoleValidator.class);
    private final JobRoleService jobRoleService = new JobRoleService(jobRoleDaoMock, jobRoleValidatorMock);
    private final JobRole mockedJobRoleInstance = new JobRole(1500, "Engineer", "blabla", "http://wp.pl");

    @Test
    public void createNewJobRoleSuccess() throws SQLException, FailedToCreateNewJobRoleException, JobRoleAlreadyExistsException {
        JobRoleRequest jobRoleRequest = new JobRoleRequest("TestRole5678", "Tests stuff", "some sharepoint link");
        when(jobRoleDaoMock.createNewJobRole(jobRoleRequest)).thenReturn(Optional.of(mockedJobRoleInstance));

        JobRole newJobRole = jobRoleService.createNewJobRole(jobRoleRequest);

        verify(jobRoleDaoMock).createNewJobRole(jobRoleRequest);
        assertEquals(newJobRole, mockedJobRoleInstance);
    }

    @Test
    public void createNewJobRoleFailure() throws FailedToCreateNewJobRoleException, SQLException {
        JobRoleRequest jobRoleRequest = new JobRoleRequest("TestRole5789", "Tests stuff", "link");
        when(jobRoleDaoMock.createNewJobRole(jobRoleRequest)).thenReturn(Optional.empty());

        assertThatExceptionOfType(FailedToCreateNewJobRoleException.class)
                .isThrownBy(() -> jobRoleService.createNewJobRole(jobRoleRequest));
    }

    @Test
    public void createNewJobRoleFailsWhenInputIsIncorrect() throws SQLException, FailedToCreateNewJobRoleException, InvalidJobRoleException, JobRoleAlreadyExistsException {
        JobRoleRequest mockedJobRoleRequest = new JobRoleRequest("MockedName", "MockedDescription", "MockedSPLink");
        when(jobRoleDaoMock.createNewJobRole(mockedJobRoleRequest)).thenReturn(Optional.of(mockedJobRoleInstance));

        JobRole newJobRole = jobRoleService.createNewJobRole(mockedJobRoleRequest);

        assertThat(newJobRole).isEqualTo(mockedJobRoleInstance);
    }
}
