package org.kainos.ea.service;

import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.exception.FailedToCreateNewJobRoleException;
import org.kainos.ea.model.JobRole;
import org.kainos.ea.model.JobRoleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;

public class JobRoleService {

    private final static Logger logger = LoggerFactory.getLogger(JobRoleService.class);
    private JobRoleDao jobRoleDao;
    private JobRoleValidator jobRoleValidator;

    public JobRoleService(JobRoleDao jobRoleDao, JobRoleValidator jobRoleValidator) {
        this.jobRoleDao = jobRoleDao;
        this.jobRoleValidator = jobRoleValidator;
    }

    public JobRoleService(JobRoleDao jobRoleDao, DatabaseConnector databaseConnector) {
    }

    public JobRole createNewJobRole(JobRoleRequest jobRole) throws FailedToCreateNewJobRoleException, SQLException {

        Optional<JobRole> optionalJobRole = jobRoleDao.createNewJobRole(jobRole);

        if (optionalJobRole.isEmpty()) {
            throw new FailedToCreateNewJobRoleException();
        }
        return optionalJobRole.get();
    }
}
