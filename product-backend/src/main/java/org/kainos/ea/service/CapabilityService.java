package org.kainos.ea.service;

import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.CapabilityDoesNotExistException;
import org.kainos.ea.exception.FailedToCreateCapabilityLeadException;
import org.kainos.ea.exception.FailedToGetCapabilityException;
import org.kainos.ea.exception.InvalidCapabilityLeadException;
import org.kainos.ea.model.Capability;
import org.kainos.ea.model.CapabilityRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class CapabilityService {

    private final static Logger logger = LoggerFactory.getLogger(CapabilityService.class);
    private DatabaseConnector databaseConnector;
    private CapabilityDao capabilityDao;

    private CapabilityValidator capabilityValidator = new CapabilityValidator();

    public CapabilityService(DatabaseConnector databaseConnector, CapabilityDao capabilityDao) {
        this.databaseConnector = databaseConnector;
        this.capabilityDao = capabilityDao;
    }


    public List<Capability> getEveryCapabilityLead() throws CapabilityDoesNotExistException, FailedToGetCapabilityException {
        try {

            List<Capability> capabilityList = capabilityDao.getEveryCapabilityLead(databaseConnector.getConnection());
            if (capabilityList.isEmpty()) {
                throw new CapabilityDoesNotExistException();
            }
            return capabilityList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetCapabilityException();
        }

    }


    public int createCapabilityLead(CapabilityRequest capabilityRequest) throws InvalidCapabilityLeadException, FailedToCreateCapabilityLeadException, SQLException {
        try {
            Optional<String> validation = capabilityValidator.isValidCapabilityLead(capabilityRequest);

            if (validation.isPresent()) {
                logger.warn("Validation error! Error: {}", validation.get());
                throw new InvalidCapabilityLeadException(validation.get());
            }

            int id = capabilityDao.createCapabilityLead(capabilityRequest, databaseConnector.getConnection());

            if (id == -1) {
                throw new FailedToCreateCapabilityLeadException();
            }
            
            return id;
        } catch (SQLException e) {
            logger.error("SQL Exception! Error: {}", e.getMessage());
            throw new FailedToCreateCapabilityLeadException();
        }
    }
}





