package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.DatabaseConnectionException;
import org.kainos.ea.client.FailedToGetJobRoleException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;

import java.sql.SQLException;

public class JobRoleService {

    public DatabaseConnector databaseConnector;
    public JobRoleDao jobRoleDao;

    public JobRoleService(DatabaseConnector databaseConnector, JobRoleDao jobRoleDao) {
        this.databaseConnector = databaseConnector;
        this.jobRoleDao = jobRoleDao;
    }


    public JobRole getJobRoleById(int id) throws JobRoleDoesNotExistException, FailedToGetJobRoleException {
        try {
            JobRole jobRoleSingleView = jobRoleDao.getJobRoleById(id, databaseConnector.getConnection());

            if (jobRoleSingleView == null) {
                throw new JobRoleDoesNotExistException();
            }

            return jobRoleSingleView;
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetJobRoleException();
        }
    }

}