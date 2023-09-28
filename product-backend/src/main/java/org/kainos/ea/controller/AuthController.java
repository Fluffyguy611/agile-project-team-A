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
import org.kainos.ea.exception.FailedToRegisterException;
import org.kainos.ea.exception.InvalidEmailException;
import org.kainos.ea.exception.InvalidPasswordException;
import org.kainos.ea.exception.InvalidRoleIdException;
import org.kainos.ea.model.User;
import org.kainos.ea.service.AuthService;
import org.kainos.ea.service.AuthValidator;

@Tag(name = "Authorisation")
@Path("/api")
public class AuthController {
    private final AuthService authService = new AuthService(new DatabaseConnector(), new AuthDao(), new AuthValidator());

    @POST
    @Path("/auth/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        try {
            return Response.ok(authService.register(user)).build();
        } catch (FailedToRegisterException | InvalidEmailException | InvalidPasswordException |
                 InvalidRoleIdException err) {
            System.err.println(err.getMessage());
            return Response.serverError().build();
        }
    }
}
