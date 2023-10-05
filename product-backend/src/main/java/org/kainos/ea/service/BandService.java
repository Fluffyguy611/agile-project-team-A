package org.kainos.ea.service;

import org.kainos.ea.db.BandDao;
import org.kainos.ea.exception.BandAlreadyExistsException;
import org.kainos.ea.exception.FailedToCreateNewBandException;
import org.kainos.ea.model.Band;
import org.kainos.ea.model.BandRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;

public class BandService {
    private final static Logger logger = LoggerFactory.getLogger(BandService.class);
    private BandDao bandDao;
    private BandValidator bandValidator;

    public BandService(BandDao bandDao, BandValidator bandValidator) {
        this.bandDao = bandDao;
        this.bandValidator = bandValidator;
    }

    public Band createNewBand(BandRequest band) throws FailedToCreateNewBandException, SQLException, BandAlreadyExistsException {
        Optional<String> validationError = bandValidator.isValidBandName(band);

        if (validationError.isPresent()) {
            throw new FailedToCreateNewBandException(validationError.get());
        }

        Optional<Band> existingBand = bandDao.getBandByName(band.getName());

        if (existingBand.isPresent()) {
            throw new BandAlreadyExistsException();
        }

        Optional<Band> optionalBand = bandDao.createNewBand(band);

        return optionalBand.orElseThrow(() -> new FailedToCreateNewBandException("Couldn't create new band!"));
    }
}
