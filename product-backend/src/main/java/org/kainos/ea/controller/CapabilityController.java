package org.kainos.ea.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.exception.CapabilityDoesNotExistException;
import org.kainos.ea.exception.FailedToCreateCapabilityLeadException;
import org.kainos.ea.exception.FailedToGetCapabilityException;
import org.kainos.ea.exception.InvalidCapabilityLeadException;
import org.kainos.ea.service.CapabilityService;
import org.kainos.ea.service.JobRoleService;
import org.kainos.ea.model.CapabilityRequest;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Capability")
@Path("/api")
public class CapabilityController {
    private final static Logger logger = LoggerFactory.getLogger(CapabilityService.class);

    CapabilityService capabilityService = new CapabilityService(new DatabaseConnector(), new CapabilityDao());

    @GET
    @Path("/capability")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEveryCapability(@HeaderParam("Authorisation") String token) {
        try {
            return Response.ok(capabilityService.getEveryCapabilityLead()).build();
        } catch (FailedToGetCapabilityException e) {
            logger.error("Failed to get the capability! Error: {}", (e.getMessage()));

            return Response.serverError().build();
        } catch (CapabilityDoesNotExistException e) {
            logger.error("Capability does not exist! Error: {}", (e.getMessage()));

            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/capability")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDeliveryEmployee(@HeaderParam("Authorisation") String token, CapabilityRequest capabilityRequest) {
        try {
            return Response.ok(capabilityService.createCapabilityLead(capabilityRequest)).build();
        } catch (FailedToCreateCapabilityLeadException e) {
            logger.error(e.getMessage());
            return Response.serverError().build();
        }
        catch (InvalidCapabilityLeadException e) {
            logger.error("Capability does not exist! Error: {}", (e.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }

    }
}
