package org.kainos.ea.api;

import org.kainos.ea.cli.Capability;
import org.kainos.ea.cli.CapabilityRequest;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.*;
import org.kainos.ea.core.CapabilityValidator;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;

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
            Optional<List<Capability>> capabilityList = capabilityDao.getEveryCapabilityLead(databaseConnector.getConnection());

            if (capabilityList.get().isEmpty()) {
                throw new CapabilityDoesNotExistException();
            }

            return capabilityList.get();
        } catch (SQLException | DatabaseConnectionException e) {
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
        } catch (SQLException | DatabaseConnectionException e) {
            throw new FailedToCreateCapabilityLeadException();
        }
    }
}
