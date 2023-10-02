package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.DatabaseConnectionException;
import org.kainos.ea.client.FailedToGetAllJobRolesException;
import org.kainos.ea.client.FailedToGetJobRoleException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JobRoleService {
    private JobRoleDao jobRoleDao;

    private DatabaseConnector databaseConnector;

    public JobRoleService(DatabaseConnector databaseConnector, JobRoleDao jobRoleDao) {
        this.databaseConnector = databaseConnector;
        this.jobRoleDao = jobRoleDao;
    }


    public JobRoleService(JobRoleDao jobRoleDao) {
        this.jobRoleDao = jobRoleDao;
    }

    public List<JobRole> getAllJobRoles() throws SQLException, FailedToGetAllJobRolesException {

        try {
            List<JobRole> jobRoleList = jobRoleDao.getAllJobRoles();
            return jobRoleList;
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetAllJobRolesException();
        }
    }


    public JobRole getJobRoleById(int id) throws JobRoleDoesNotExistException, FailedToGetJobRoleException {
        try {
            Optional<JobRole> jobRoleSingleView = jobRoleDao.getJobRoleById(id, databaseConnector.getConnection());

            if (jobRoleSingleView.isEmpty()) {
                throw new JobRoleDoesNotExistException();
            }

            return jobRoleSingleView.get();
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetJobRoleException();
        }
    }

}
