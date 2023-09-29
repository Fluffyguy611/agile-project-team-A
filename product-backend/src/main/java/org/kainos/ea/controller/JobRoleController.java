package org.kainos.ea.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.exception.ErrorResponse;
import org.kainos.ea.exception.FailedToCreateNewJobRoleException;
import org.kainos.ea.exception.JobRoleAlreadyExistsException;
import org.kainos.ea.model.JobRoleRequest;
import org.kainos.ea.service.JobRoleService;
import org.kainos.ea.service.JobRoleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Tag(name = "Group A")
@Path("/api")

public class JobRoleController {
    private final static Logger logger = LoggerFactory.getLogger(JobRoleService.class);
    private final JobRoleService jobRoleService = new JobRoleService(new JobRoleDao(), new JobRoleValidator());

    @POST
    @Path("/admin/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewJobRole(JobRoleRequest jobRole) {
        try {
            return Response.ok(jobRoleService.createNewJobRole(jobRole)).build();
        } catch (FailedToCreateNewJobRoleException | SQLException e) {
            logger.error("Failed to create new Job Role! Error: {}", e.getMessage());

            return Response.serverError().entity(new ErrorResponse(e.getMessage())).build();
        } catch (JobRoleAlreadyExistsException e) {
            String errorMessage = "Job Role already exists!" + e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse(errorMessage);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }
}
