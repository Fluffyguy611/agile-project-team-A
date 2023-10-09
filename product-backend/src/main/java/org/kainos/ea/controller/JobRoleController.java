package org.kainos.ea.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.JobRoleRequest;
import org.kainos.ea.service.CapabilityService;
import org.kainos.ea.service.JobRoleService;
import org.kainos.ea.service.JobRoleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Tag(name = "Job Roles")
@Path("/api")

public class JobRoleController {
    private final static Logger logger = LoggerFactory.getLogger(JobRoleService.class);

    private final JobRoleService jobRoleService = new JobRoleService(new JobRoleDao(), new JobRoleValidator());
    private final CapabilityService capabilityService = new CapabilityService(new DatabaseConnector(), new CapabilityDao());


    @POST
    @Path("/admin/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewJobRole(JobRoleRequest jobRole) {
        try {
            return Response.ok(jobRoleService.createNewJobRole(jobRole)).build();
        } catch (FailedToCreateNewJobRoleException | SQLException e) {
            logger.error("Failed to create new Job Role! Error: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (JobRoleAlreadyExistsException e) {
            String errorMessage = "Job Role already exists!";
            ErrorResponse errorResponse = new ErrorResponse(errorMessage);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @GET
    @Path("/job-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllJobRoles() {
        try {
            return Response.ok(jobRoleService.getAllJobRoles()).build();
        } catch (FailedToGetAllJobRolesException | SQLException e) {
            logger.error("Failed to get Job Roles! Error: {}", e.getMessage());
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


    @GET
    @Path("/all-capability")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCapability() {
        try {
            return Response.ok(capabilityService.getEveryCapabilityLead()).build();
        } catch (FailedToGetCapabilityException e) {
            logger.error("Failed to get the job Role! Error: {}", (e.getMessage()));
            return Response.serverError().build();
        } catch (CapabilityDoesNotExistException |
                 FailedToCreateCapabilityLeadException e) {
            logger.error("capability does not exist! Error: {}", (e.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();


        }
    }
}

