package org.kainos.ea.service;

import org.kainos.ea.exception.*;
import org.kainos.ea.model.Capability;
import org.kainos.ea.model.CapabilityRequest;
import org.kainos.ea.core.CapabilityValidator;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CapabilityService {

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


    public int createCapabilityLead(CapabilityRequest capabilityRequest) throws InvalidCapabilityLeadException, FailedToCreateCapabilityLeadException {
        try {
            String validation = capabilityValidator.isValidCapabilityLead(capabilityRequest);

            if (validation != null) {
                throw new InvalidCapabilityLeadException();
            }

            int id = capabilityDao.createCapabilityLead(capabilityRequest, databaseConnector.getConnection());

            if (id == -1) {
                throw new FailedToCreateCapabilityLeadException();
            }

            return id;
        } catch (SQLException e) {
            throw new FailedToCreateCapabilityLeadException();
        }
    }
}
