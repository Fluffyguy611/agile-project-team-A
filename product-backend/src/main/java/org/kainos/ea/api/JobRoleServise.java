package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.FailedToGetAllJobRolesException;
import org.kainos.ea.db.JobRolesDao;

import java.sql.SQLException;
import java.util.List;

public class JobRoleServise {
    private JobRolesDao jobRolesDao = new JobRolesDao();

    public List<JobRole> getAllJobRoles() throws FailedToGetAllJobRolesException {

        try {
            List<JobRole> jobRoleList = jobRolesDao.getAllJobRoles();
            return jobRoleList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetAllJobRolesException();
        }
    }
}
