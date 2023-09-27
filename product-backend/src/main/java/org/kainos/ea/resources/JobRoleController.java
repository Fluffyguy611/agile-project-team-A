package org.kainos.ea.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.api.JobRoleServise;
import org.kainos.ea.client.FailedToGetAllJobRolesException;


@Path("/api")
public class JobRoleController {

    private JobRoleServise jobRoleServise = new JobRoleServise();

    @GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllJobRoles() {
        try {
            return Response.ok(jobRoleServise.getAllJobRoles()).build();
        } catch (FailedToGetAllJobRolesException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

}