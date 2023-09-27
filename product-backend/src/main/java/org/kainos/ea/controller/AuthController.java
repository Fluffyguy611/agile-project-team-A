package org.kainos.ea.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.cli.User;
import org.kainos.ea.client.FailedToRegisterException;
import org.kainos.ea.client.InvalidEmailException;
import org.kainos.ea.client.InvalidUserException;

@Tag(name = "Authorisation")
@Path("/api")
public class AuthController {
    private AuthService authService = new AuthService();
    @POST
    @Path("/auth/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user){
        try{
            return Response.ok(authService.register(user)).build();
        } catch (FailedToRegisterException | InvalidEmailException | InvalidUserException err){
            System.err.println(err.getMessage());
            return Response.serverError().build();
        }
    }
}
