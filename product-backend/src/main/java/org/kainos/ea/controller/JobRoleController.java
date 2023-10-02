package org.kainos.ea.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.client.FailedToGetAllJobRolesException;
import org.kainos.ea.client.FailedToGetJobRoleException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Tag(name = "Job Roles")
@Path("/api")
public class JobRoleController {
    private final static Logger logger = LoggerFactory.getLogger(JobRoleService.class);
    JobRoleService jobRoleService = new JobRoleService(new DatabaseConnector(), new JobRoleDao());


    @GET
    @Path("/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllJobRoles() {
        try {
            return Response.ok(jobRoleService.getAllJobRoles()).build();
        } catch (FailedToGetAllJobRolesException | SQLException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/job-roles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobRoleById(@PathParam("id") int id) {
        try {
            return Response.ok(jobRoleService.getJobRoleById(id)).build();
        } catch (FailedToGetJobRoleException e) {
            logger.error("Failed to get the job Role! Error: {}", (e.getMessage()));

            return Response.serverError().build();
        } catch (JobRoleDoesNotExistException e) {
            logger.error("Job Role does not exist! Error: {}", (e.getMessage()));

            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }
    }
}
