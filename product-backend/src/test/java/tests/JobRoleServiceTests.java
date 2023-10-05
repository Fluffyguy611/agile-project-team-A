package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.JobRole;
import org.kainos.ea.model.JobRoleRequest;
import org.kainos.ea.service.JobRoleService;
import org.kainos.ea.service.JobRoleValidator;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    public void createNewJobRole_When_JobAlreadyExist_Then_Expect_JobAlreadyExistExceptionToBeThrown() throws SQLException, FailedToCreateNewJobRoleException, InvalidJobRoleException, JobRoleAlreadyExistsException {
        JobRoleRequest mockedJobRoleRequest = new JobRoleRequest("MockedName", "MockedDescription", "MockedSPLink");
        when(jobRoleDaoMock.createNewJobRole(mockedJobRoleRequest)).thenReturn(Optional.of(mockedJobRoleInstance));
        when(jobRoleDaoMock.getJobRoleByName("MockedName")).thenReturn(Optional.of(new JobRole(1, "", "", "")));

        assertThatExceptionOfType(JobRoleAlreadyExistsException.class)
                .isThrownBy(() -> jobRoleService.createNewJobRole(mockedJobRoleRequest));
    }

    @Test
    void getJobRole_shouldThrowFailedToGetJobRoleException_whenDaoThrowsSQLException() throws FailedToGetJobRoleException, DatabaseConnectionException, SQLException {
        int jobRoleId = 1;
        Mockito.when(jobRoleDaoMock.getJobRoleById(jobRoleId)).thenThrow(SQLException.class);

        assertThrows(FailedToGetJobRoleException.class,
                () -> jobRoleService.getJobRoleById(jobRoleId));
    }


    @Test
    void getJobRole_shouldReturnJobRole_whenDaoReturnsAJobRole() throws DatabaseConnectionException, SQLException,
            JobRoleDoesNotExistException, FailedToGetJobRoleException {
        JobRole jobRole = new JobRole(
                1,
                "Principal",
                "This is a test case",
                "https://example.com"
        );
        int jobRoleId = 1;
        Mockito.when(jobRoleDaoMock.getJobRoleById(jobRoleId)).thenReturn(Optional.of(jobRole));

        JobRole resultRole = jobRoleService.getJobRoleById(jobRoleId);

        assertEquals(resultRole, jobRole);
    }

    @Test
    void getJobRole_shouldThrowUserJobRoleNotExistException_whenDaoReturnsNull() throws SQLException, DatabaseConnectionException, JobRoleDoesNotExistException {
        int id = 1;
        Mockito.when(jobRoleDaoMock.getJobRoleById(id)).thenReturn(Optional.empty());

        assertThrows(JobRoleDoesNotExistException.class,
                () -> jobRoleService.getJobRoleById(id));
    }

    @Test
    void getJobRoles_shouldReturnJobRoles_whenDaoReturnsJobRoles() throws SQLException, FailedToGetAllJobRolesException, DatabaseConnectionException {
        List<JobRole> mockedJobRole = new ArrayList<>();

        mockedJobRole.add(new JobRole(
                1,
                "name",
                "description",
                "sharePointLink"

        ));

        Mockito.when(jobRoleDaoMock.getAllJobRoles()).thenReturn(mockedJobRole);

        List<JobRole> result = jobRoleService.getAllJobRoles();
        assertEquals(result, mockedJobRole);
        verify(jobRoleDaoMock).getAllJobRoles();
    }

    @Test
    void getJobRole_shouldThrowsFailedToGetAllJobRolesException_whenFailedToGetAllJobs() throws SQLException, DatabaseConnectionException {
        Mockito.when(jobRoleDaoMock.getAllJobRoles()).thenThrow(SQLException.class);

        assertThatExceptionOfType(FailedToGetAllJobRolesException.class)
                .isThrownBy(() -> jobRoleService.getAllJobRoles())
                .withMessage("Failed to get job all job roles");

    }
}
