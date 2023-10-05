package org.kainos.ea.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.service.CapabilityService;
import org.kainos.ea.service.JobRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Capability")
@Path("/api")
public class CapabilityController {
    private final static Logger logger = LoggerFactory.getLogger(JobRoleService.class);

    CapabilityService capabilityService = new CapabilityService(new DatabaseConnector(), new CapabilityDao());

//    @GET
//    @Path("/capability")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getEveryCapability() {
//        try {
//            return Response.ok(capabilityService.getEveryCapabilityLead()).build();
//        } catch (FailedToGetCapabilityException e) {
//            logger.error("Failed to get the capability! Error: {}", (e.getMessage()));
//
//            return Response.serverError().build();
//        } catch (CapabilityDoesNotExistException e) {
//            logger.error("Capability does not exist! Error: {}", (e.getMessage()));
//
//            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
//        }
//    }

//    @POST
//    @Path("/admin/capability")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response createDeliveryEmployee(CapabilityRequest capabilityRequest) {
//        try {
//            return Response.ok(capabilityService.createCapabilityLead(capabilityRequest)).build();
//        } catch (FailedToCreateCapabilityLeadException e) {
//            logger.error("Failed to create Capability! Error: {}", e.getMessage());
//            return Response.serverError().build(); //500
//        } catch (InvalidCapabilityLeadException e) {
//            logger.error("Capability does not exist! Error: {}", (e.getMessage()));
//            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();//400 np bledne zapytanie
//        }
//
//    }

}
