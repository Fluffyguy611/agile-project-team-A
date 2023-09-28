package org.kainos.ea.service;

import org.kainos.ea.model.JobRoleRequest;

public class JobRoleValidator {

    public String isValidJobRole(JobRoleRequest jobRole) {

        if (jobRole.getName().length() > 64) {
            return "Name longer than 64 characters";
        }

        if (jobRole.getDescription().length() > 2000) {
            return "Description longer than 2000 characters";
        }

        if (jobRole.getSharePointLink().length() > 2137) {
            return "SharePointLink longer than 2137 characters";
        }

        return null;
    }
}
