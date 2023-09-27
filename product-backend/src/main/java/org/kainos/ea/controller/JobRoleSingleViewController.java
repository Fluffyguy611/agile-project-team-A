package org.kainos.ea.resources;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.swagger.annotations.Api;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleSingleViewDao;
import org.kainos.ea.exception.DatabaseConnectionException;
import org.kainos.ea.exception.JobRoleDoesNotExistException;
import org.kainos.ea.service.JobRoleSingleViewService;

import java.sql.SQLException;
@Api("Single JobRole View")
@Path("/api")
public class JobRoleSingleViewController {

    JobRoleSingleViewService jobRoleSingleViewService = new JobRoleSingleViewService(new JobRoleSingleViewDao(), new DatabaseConnector());


    @GET
    @Path("/jobRoles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobRoleById(@PathParam("id") int id) {
        try {
            return Response.ok(jobRoleSingleViewService.getJobRoleById(id)).build();
        } catch (DatabaseConnectionException | SQLException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        } catch (JobRoleDoesNotExistException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
