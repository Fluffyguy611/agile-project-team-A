package org.kainos.ea.core;

import org.kainos.ea.cli.JobRoleSingleView;

public class JobRoleSingleViewValidator {

    public String isValidJobRole(JobRoleSingleView jobRoleSingleView){
        if (jobRoleSingleView.getSharePointLink().length() > 2136){
            return "Link is not allowed";
        }

        return null;
    }
}
