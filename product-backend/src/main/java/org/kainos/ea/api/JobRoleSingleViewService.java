package org.kainos.ea.api;

import org.kainos.ea.cli.JobRoleSingleViewA;
import org.kainos.ea.client.DatabaseConnectionExceptionA;
import org.kainos.ea.client.FailedToGetJobRoleException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.db.DatabaseConnectorA;
import org.kainos.ea.db.JobRoleSingleViewDaoA;

import java.sql.SQLException;

public class JobRoleSingleViewService {

    public DatabaseConnectorA databaseConnector;
    public JobRoleSingleViewDaoA jobRoleSingleViewDao;

    public JobRoleSingleViewService(DatabaseConnectorA databaseConnector, JobRoleSingleViewDaoA jobRoleSingleViewDao) {
        this.databaseConnector = databaseConnector;
        this.jobRoleSingleViewDao = jobRoleSingleViewDao;
    }


    public JobRoleSingleViewA getJobRoleSingleViewById(int id) throws JobRoleDoesNotExistException, FailedToGetJobRoleException {
        try {
            JobRoleSingleViewA jobRoleSingleView = jobRoleSingleViewDao.getJobRoleById(id, databaseConnector.getConnection());

            if(jobRoleSingleView == null){
                throw new JobRoleDoesNotExistException();
            }

            return jobRoleSingleView;
        } catch (SQLException | DatabaseConnectionExceptionA e) {
            System.err.println(e.getMessage());

            throw new FailedToGetJobRoleException();
        }
    }

}
