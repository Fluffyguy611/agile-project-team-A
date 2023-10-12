package com.kainos.ea.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.exception.DatabaseConnectionException;
import org.kainos.ea.exception.FailedToGetJobRoleException;
import org.kainos.ea.exception.JobRoleDoesNotExistException;
import org.kainos.ea.model.JobRole;
import org.kainos.ea.model.JobRolePlusBandResponse;
import org.kainos.ea.service.JobRoleService;
import org.kainos.ea.service.JobRoleValidator;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class JobRoleServiceTest {
    private final JobRoleValidator jobRoleValidatorMock = mock(JobRoleValidator.class);
    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
    JobRoleService jobRoleService = new JobRoleService(jobRoleDao, jobRoleValidatorMock);


    JobRole jobRole = new JobRole(
            1,
            "Principal",
            "This is a test case",
            "https://example.com",
            1
    );

    @Test
    void getJobRole_shouldThrowFailedToGetJobRoleException_whenDaoThrowsSQLException() throws FailedToGetJobRoleException, DatabaseConnectionException, SQLException {
        int jobRoleId = 1;
        Mockito.when(jobRoleDao.getJobRoleById(jobRoleId)).thenThrow(SQLException.class);

        assertThrows(FailedToGetJobRoleException.class,
                () -> jobRoleService.getJobRoleById(jobRoleId));
    }


    @Test
    void getJobRole_shouldReturnJobRole_whenDaoReturnsAJobRole() throws DatabaseConnectionException, SQLException,
            JobRoleDoesNotExistException, FailedToGetJobRoleException {
        JobRolePlusBandResponse jobRolePlusBandResponse = new JobRolePlusBandResponse(
                1,
                "Principal",
                "This is a test case",
                "https://example.com",
                1,
                "Some band name",
                4
        );
        int jobRoleId = 1;
        Mockito.when(jobRoleDao.getJobRoleById(jobRoleId)).thenReturn(Optional.of(jobRolePlusBandResponse));

        JobRolePlusBandResponse resultRole = jobRoleService.getJobRoleById(jobRoleId);

        assertEquals(resultRole, jobRolePlusBandResponse);
    }

    @Test
    void getJobRole_shouldThrowUserJobRoleNotExistException_whenDaoReturnsNull() throws SQLException, DatabaseConnectionException, JobRoleDoesNotExistException {
        int id = 1;
        Mockito.when(jobRoleDao.getJobRoleById(id)).thenReturn(Optional.empty());

        assertThrows(JobRoleDoesNotExistException.class,
                () -> jobRoleService.getJobRoleById(id));
    }
}
