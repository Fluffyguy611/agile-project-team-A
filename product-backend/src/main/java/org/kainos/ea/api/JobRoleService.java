package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.FailedToGetAllJobRolesException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;

import java.sql.SQLException;
import java.util.List;

public class JobRoleService {
    private JobRoleDao jobRoleDao;

    public JobRoleService(JobRoleDao jobRoleDao) {
        this.jobRoleDao = jobRoleDao;
    }

    public List<JobRole> getAllJobRoles() throws SQLException, FailedToGetAllJobRolesException {

        try {
            List<JobRole> jobRoleList = jobRoleDao.getAllJobRoles();
            return jobRoleList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetAllJobRolesException();
        }
    }
}


