package org.kainos.ea.service;

import org.kainos.ea.model.JobRoleRequest;

import java.util.Optional;

public class JobRoleValidator {

    public Optional<String> isValidJobRole(JobRoleRequest jobRole) {

        if (jobRole.getName().length() > 64) {
            return Optional.of("Name longer than 64 characters");
        } else if (jobRole.getName().length() < 2) {
            return Optional.of("Name shorter than 2 characters");
        }

        if (jobRole.getDescription().length() > 2000) {
            return Optional.of("Description longer than 2000 characters");
        } else if (jobRole.getDescription().length() < 5) {
            return Optional.of("Description shorter than 5 characters");
        }

        if (jobRole.getSharePointLink().length() > 2137) {
            return Optional.of("SharePointLink longer than 2137 characters");
        } else if (!jobRole.getSharePointLink().matches("^(http://|https://).*$")) {
            return Optional.of("Invalid URL");
        }

        return Optional.empty();
    }
}
