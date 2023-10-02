package org.kainos.ea.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.User;
import org.kainos.ea.service.AuthService;
import org.kainos.ea.service.AuthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Authorisation")
@Path("/api")
public class AuthController {
    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AuthService authService = new AuthService(new DatabaseConnector(), new AuthDao(), new AuthValidator());

    @POST
    @Path("/auth/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        try {
            return Response.ok(authService.register(user)).build();
        } catch (FailedToRegisterException e) {
            logger.error("Failed to create user! Error: {}", e.getMessage());

            return Response.serverError().entity(new ErrorResponse(e.getMessage())).build();
        } catch (InvalidEmailException | InvalidPasswordException | InvalidRoleIdException e) {
            logger.error("Invalid user data! Error: {}", e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }
    }
}
