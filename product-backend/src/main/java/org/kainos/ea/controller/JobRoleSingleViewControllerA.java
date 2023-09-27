package org.kainos.ea.controller;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.client.FailedToGetJobRoleException;
import org.kainos.ea.db.DatabaseConnectorA;
import org.kainos.ea.db.JobRoleSingleViewDaoA;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.api.JobRoleSingleViewService;

@Path("/api")
public class JobRoleSingleViewControllerA {

    JobRoleSingleViewService jobRoleSingleViewService = new JobRoleSingleViewService(new DatabaseConnectorA(), new JobRoleSingleViewDaoA());


    @GET
    @Path("/jobRoles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobRoleById(@PathParam("id") int id) {
        try {
            return Response.ok(jobRoleSingleViewService.getJobRoleSingleViewById(id)).build();
        } catch ( FailedToGetJobRoleException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        } catch (JobRoleDoesNotExistException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
