package org.kainos.ea.controller;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.client.FailedToGetJobRoleException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api")
public class JobRoleController {
    private final static Logger logger = LoggerFactory.getLogger(JobRoleService.class);

    JobRoleService jobRoleService = new JobRoleService(new DatabaseConnector(), new JobRoleDao());


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

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
