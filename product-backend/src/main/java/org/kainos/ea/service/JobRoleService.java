package org.kainos.ea.service;

<<<<<<< HEAD
import org.kainos.ea.exception.FailedToGetJobRoleException;
import org.kainos.ea.exception.JobRoleDoesNotExistException;
=======
>>>>>>> main
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.JobRole;
import org.kainos.ea.model.JobRoleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
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
            logger.error("SQL exception! Error: {}", e.getMessage());
            throw new FailedToGetJobRoleException();
        }
    }

    public List<JobRole> getAllJobRoles() throws SQLException, FailedToGetAllJobRolesException {

        try {
            List<JobRole> jobRoleList = jobRoleDao.getAllJobRoles();
            return jobRoleList;
        } catch (SQLException | DatabaseConnectionException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());
            throw new FailedToGetAllJobRolesException();
        }
    }
}
