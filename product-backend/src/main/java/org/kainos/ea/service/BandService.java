package org.kainos.ea.service;

import org.kainos.ea.db.BandDao;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.Band;
import org.kainos.ea.model.BandRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BandService {
    private final static Logger logger = LoggerFactory.getLogger(BandService.class);
    private BandDao bandDao;
    private BandValidator bandValidator;

    public BandService(BandDao bandDao, BandValidator bandValidator) {
        this.bandDao = bandDao;
        this.bandValidator = bandValidator;
    }

    public Band createNewBand(BandRequest band) throws FailedToCreateNewBandException, SQLException, BandAlreadyExistsException, FailedToCreateNewBandInvalidLevelException {
        Optional<String> validationError = bandValidator.isValidBandName(band);
        Optional<Integer> validationLevelError = bandValidator.isValidBandLevel(band);

        if (validationError.isPresent()) {
            throw new FailedToCreateNewBandException(validationError.get());
        }

        if (validationLevelError.isPresent()) {
            throw new FailedToCreateNewBandInvalidLevelException(validationLevelError.get());
        }

        Optional<Band> existingBand = bandDao.getBandByName(band.getName());

        if (existingBand.isPresent()) {
            throw new BandAlreadyExistsException();
        }

        Optional<Band> optionalBand = bandDao.createNewBand(band);

        return optionalBand.orElseThrow(() -> new FailedToCreateNewBandException("Couldn't create new band!"));
    }

    public Band getBandById(int id) throws BandDoesNotExistException, FailedToGetBandException {
        try {
            Optional<Band> bandSingleView = bandDao.getBandById(id);

            if (bandSingleView.isEmpty()) {
                throw new BandDoesNotExistException();
            }

            return bandSingleView.get();
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetBandException();
        }
    }

    public List<Band> getAllJobBands() throws SQLException, FailedToGetAllJobBandsException {

        try {
            List<Band> bandList = bandDao.getAllJobBands();
            return bandList;
        } catch (SQLException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());
            throw new FailedToGetAllJobBandsException();
        }
    }
}
