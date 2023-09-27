package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;

import java.util.ArrayList;
import java.util.List;

public class JobRoleServise {
    public List<JobRole>getAllJobRoles(){
        List<JobRole> jobRoleList = new ArrayList<>();

        JobRole role1 = new JobRole(1,"name", "description", "sharepointlink");

        jobRoleList.add(role1);

        return  jobRoleList;
    }
}
