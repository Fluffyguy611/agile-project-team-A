package org.kainos.ea.controller;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.kainos.ea.exception.NoActiveTokenException;
import org.kainos.ea.service.CapabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Provider
public class RestrictedOperationsRequestFilter implements ContainerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(CapabilityService.class);

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        if (ctx.getCookies().isEmpty()) {
            logger.error("No token!");
            ctx.abortWith(Response.status(Response.Status.FORBIDDEN).entity("No token").build());
        }
    }
}
