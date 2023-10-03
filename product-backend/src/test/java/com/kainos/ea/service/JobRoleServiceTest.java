package com.kainos.ea.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.DatabaseConnectionException;
import org.kainos.ea.client.FailedToGetJobRoleException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class JobRoleServiceTest {
    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);

    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);

    JobRoleService jobRoleService = new JobRoleService(databaseConnector, jobRoleDao);


    JobRole jobRole = new JobRole(
            1,
            "Principal",
            "This is a test case",
            "https://example.com"
    );


    Connection conn;

    @Test
    void getJobRole_shouldThrowFailedToGetJobRoleException_whenDaoThrowsSQLException() throws FailedToGetJobRoleException, DatabaseConnectionException, SQLException {
        int jobRoleId = 1;
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.getJobRoleById(jobRoleId, conn)).thenThrow(SQLException.class);

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
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.getJobRoleById(jobRoleId, conn)).thenReturn(Optional.of(jobRole));

        JobRole resultRole = jobRoleService.getJobRoleById(jobRoleId);

        assertEquals(resultRole, jobRole);
    }

    @Test
    void getJobRole_shouldThrowUserJobRoleNotExistException_whenDaoReturnsNull() throws SQLException, DatabaseConnectionException, JobRoleDoesNotExistException {
        int Id = 1;
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.getJobRoleById(Id, conn)).thenReturn(Optional.empty());

        assertThrows(JobRoleDoesNotExistException.class,
                () -> jobRoleService.getJobRoleById(Id));
    }
}
