package org.kainos.ea.service;

import org.kainos.ea.client.FailedToGetJobRoleException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.exception.FailedToCreateNewJobRoleException;
import org.kainos.ea.exception.JobRoleAlreadyExistsException;
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

    public JobRole createNewJobRole(JobRoleRequest jobRole) throws FailedToCreateNewJobRoleException, SQLException, JobRoleAlreadyExistsException {
        Optional<String> validationError = jobRoleValidator.isValidJobRole(jobRole);
        
        if (validationError.isPresent()) {
            throw new FailedToCreateNewJobRoleException(validationError.get());
        }

        Optional<JobRole> existingJobRole = jobRoleDao.getJobRoleByName(jobRole.getName());

        if (existingJobRole.isPresent()) {
            throw new JobRoleAlreadyExistsException();
        }

        Optional<JobRole> optionalJobRole = jobRoleDao.createNewJobRole(jobRole);

        return optionalJobRole.orElseThrow(() -> new FailedToCreateNewJobRoleException("Couldn't create job role!"));
    }

    public JobRole getJobRoleById(int id) throws JobRoleDoesNotExistException, FailedToGetJobRoleException {
        try {
            Optional<JobRole> jobRoleSingleView = jobRoleDao.getJobRoleById(id);

            if (jobRoleSingleView.isEmpty()) {
                throw new JobRoleDoesNotExistException();
            }

            return jobRoleSingleView.get();
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetJobRoleException();
        }
    }
}
