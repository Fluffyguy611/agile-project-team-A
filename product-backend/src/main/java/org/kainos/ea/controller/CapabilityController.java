package org.kainos.ea.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.CapabilityRequest;
import org.kainos.ea.service.CapabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Capability")
@Path("/api")
public class CapabilityController {
    private final static Logger logger = LoggerFactory.getLogger(CapabilityController.class);

    CapabilityService capabilityService = new CapabilityService(new DatabaseConnector(), new CapabilityDao());

    @GET
    @Path("/capability")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEveryCapability() {
        try {
            return Response.ok(capabilityService.getEveryCapabilityLead()).build();
        } catch (FailedToGetCapabilityException e) {
            logger.error("Failed to get the capability! Error: {}", (e.getMessage()));

            return Response.serverError().build();
        } catch (CapabilityDoesNotExistException e) {
            logger.error("Capability does not exist! Error: {}", (e.getMessage()));

            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (FailedToCreateCapabilityLeadException e) {
            throw new RuntimeException(e);
        }
    }

    @POST

    @Path("/admin/add-capability")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCapability(CapabilityRequest capabilityRequest) {
        try {
            int result = capabilityService.createCapabilityLead(capabilityRequest);
            return Response.ok(result).build();
        } catch (FailedToCreateCapabilityLeadException e) {
            logger.error("Failed to create Capability! Error: {}", e.getMessage());
            return Response.serverError().build(); //500
        } catch (InvalidCapabilityLeadException e) {
            logger.error("Capability does not exist! Error: {}", (e.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();//400 np bledne zapytanie
        }

    }

}
