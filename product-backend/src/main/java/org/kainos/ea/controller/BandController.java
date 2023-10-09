package org.kainos.ea.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.db.BandDao;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.BandRequest;
import org.kainos.ea.service.BandService;
import org.kainos.ea.service.BandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Tag(name = "Job Band")
@Path("/api")

public class BandController {
    private final static Logger logger = LoggerFactory.getLogger(BandService.class);
    private final BandService bandService = new BandService(new BandDao(), new BandValidator());

    @POST
    @Path("/admin/band")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewBand(BandRequest band) {
        try {
            return Response.ok(bandService.createNewBand(band)).build();
        } catch (FailedToCreateNewBandException | SQLException | FailedToCreateNewBandInvalidLevelException e) {
            logger.error("Failed to create new Band! Error: {}", e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (BandAlreadyExistsException e) {
            String errorMessage = "Band already exists!";
            ErrorResponse errorResponse = new ErrorResponse(errorMessage);
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @GET
    @Path("/band/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBandById(@PathParam("id") int id) {
        try {
            return Response.ok(bandService.getBandById(id)).build();
        } catch (FailedToGetBandException | BandDoesNotExistException e) {
            logger.error("Failed to get the Band! Error: {}", (e.getMessage()));

            return Response.serverError().build();
        }
    }

    @GET
    @Path("/admin/bands")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllJobBands() {
        try {
            return Response.ok(bandService.getAllJobBands()).build();
        } catch (FailedToGetAllJobBandsException | SQLException e) {
            logger.error("Failed to get Job Roles! Error: {}", e.getMessage());
            return Response.serverError().build();
        }
    }
}
