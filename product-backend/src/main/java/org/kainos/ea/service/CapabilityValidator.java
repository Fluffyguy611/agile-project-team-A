package org.kainos.ea.service;


import org.kainos.ea.model.CapabilityRequest;

import java.util.Optional;

public class CapabilityValidator {
    public Optional<String> isValidCapabilityLead(CapabilityRequest capability) {

//        if (capability.getCapability().length() > 64) {
//            return Optional.of("Capability can't be longer than 64 characters");
//        } else if (capability.getCapability().isEmpty()) {
//            return Optional.of("Capability name cannot be empty");
//        }
//
//        if (capability.getName().length() > 2000) {
//            return Optional.of("Name longer than 2000 characters");
//        } else if (capability.getName().isEmpty()) {
//            return Optional.of("Lead name cannot be empty");
//        }
//
//        if (capability.getPhoto().length() > 429496729) {
//            return Optional.of("Photo is bigger than 4GB");
//        } else if (capability.getPhoto().isEmpty()) {
//            return Optional.of("Photo must be attached!");
//        }
//
        return Optional.empty();
    }


}
