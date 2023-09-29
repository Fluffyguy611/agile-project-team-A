package org.kainos.ea.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.client.FailedToGetAllJobRolesException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;

import java.sql.SQLException;


@Path("/api")
public class JobRoleController {

    private DatabaseConnector databaseConnector = new DatabaseConnector();
    private JobRoleDao jobRoleDao = new JobRoleDao(databaseConnector);
    private JobRoleService jobRoleService = new JobRoleService(jobRoleDao);

    @GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllJobRoles() {
        try {
            return Response.ok(jobRoleService.getAllJobRoles()).build();
        } catch (FailedToGetAllJobRolesException | SQLException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

}