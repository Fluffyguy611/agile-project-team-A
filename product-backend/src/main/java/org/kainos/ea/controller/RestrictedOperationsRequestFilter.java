package org.kainos.ea.controller;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.kainos.ea.core.Secrets;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.FailedToVerifyTokenException;
import org.kainos.ea.exception.NoActiveTokenException;
import org.kainos.ea.exception.UserDoesNotExistException;
import org.kainos.ea.model.Role;
import org.kainos.ea.model.User;
import org.kainos.ea.service.CapabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.time.DateUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class RestrictedOperationsRequestFilter implements ContainerRequestFilter {

     private final static Logger logger = LoggerFactory.getLogger(RestrictedOperationsRequestFilter.class);

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        String url = ctx.getUriInfo().getRequestUri().getPath();

        if(url.contains("/api/auth") || url.contains("swagger") || url.contains("/openapi.json")){
            return;
        }

        if (!ctx.getHeaders().containsKey("Authorisation")){
            logger.error("Failed to get token!");
            ctx.abortWith(Response.status(Response.Status.FORBIDDEN).entity("No token").build());
            return;
        }
        try{
            DecodedJWT decodedJWT = getDecodedJWT(ctx);

            if (url.contains("/api/admin/")){
                Integer roleClaim = decodedJWT.getClaim("role").asInt();

                if (roleClaim == Role.EMPLOYEE){
                    logger.error("No admin rights");
                    ctx.abortWith(Response.status(Response.Status.FORBIDDEN).entity("No admin rights").build());
                }
            }


        }catch (FailedToVerifyTokenException e){
            ctx.abortWith(Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build());
        }
    }

    public DecodedJWT getDecodedJWT(ContainerRequestContext ctx) throws FailedToVerifyTokenException {
        Algorithm algorithm = Algorithm.HMAC256(Secrets.TOKEN_SECRET);
        String token = ctx.getHeaderString("Authorisation");
        DecodedJWT decodedJWT;
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception){
            throw new FailedToVerifyTokenException();
        }
        return decodedJWT;
    }
}
