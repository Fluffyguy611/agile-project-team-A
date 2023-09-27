package org.kainos.ea.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.kainos.ea.api.JobRoleServise;
import org.kainos.ea.cli.JobRole;

import java.util.List;

@Path("/api")
public class JobRoleController {

    private JobRoleServise jobRoleServise = new JobRoleServise();

    @GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobRole> getAllJobRoles() {
        return jobRoleServise.getAllJobRoles();
    }
}