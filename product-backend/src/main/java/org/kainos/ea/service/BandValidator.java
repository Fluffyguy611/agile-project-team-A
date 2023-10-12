package org.kainos.ea.service;

import org.kainos.ea.model.BandRequest;

import java.util.Optional;

public class BandValidator {

    public Optional<String> isValidBandName(BandRequest band) {

        if (band.getName().length() > 64) {
            return Optional.of("Name longer than 64 characters");
        } else if (band.getName().length() < 2) {
            return Optional.of("Name shorter than 2 characters");
        }
        return Optional.empty();
    }

    public Optional<String> isValidBandLevel(BandRequest band) {

        if (band.getLevel() >= 0 && band.getLevel() <= 9) {
            return Optional.empty();
        } else {
            return Optional.of("Incorrect Level input");
        }
    }
}
