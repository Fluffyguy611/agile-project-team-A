package org.kainos.ea.api;

import org.kainos.ea.cli.JobRoleSingleView;
import org.kainos.ea.client.DatabaseConnectionException;
import org.kainos.ea.client.FailedToGetJobRoleException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleSingleViewDao;

import java.sql.SQLException;

public class JobRoleSingleViewService {

    public DatabaseConnector databaseConnector;
    public JobRoleSingleViewDao jobRoleSingleViewDao;

    public JobRoleSingleViewService(DatabaseConnector databaseConnector, JobRoleSingleViewDao jobRoleSingleViewDao) {
        this.databaseConnector = databaseConnector;
        this.jobRoleSingleViewDao = jobRoleSingleViewDao;
    }


    public JobRoleSingleView getJobRoleSingleViewById(int id) throws JobRoleDoesNotExistException, FailedToGetJobRoleException {
        try {
            JobRoleSingleView jobRoleSingleView = jobRoleSingleViewDao.getJobRoleById(id, databaseConnector.getConnection());

            if(jobRoleSingleView == null){
                throw new JobRoleDoesNotExistException();
            }

            return jobRoleSingleView;
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetJobRoleException();
        }
    }

}
