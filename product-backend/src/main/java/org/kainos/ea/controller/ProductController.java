package org.kainos.ea.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kainos.ea.db.ProductDao;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.ProductRequest;
import org.kainos.ea.service.ProductService;
import org.kainos.ea.service.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Tag(name = "Engineering Academy Dropwizard Product API")
@Path("/api")
public class ProductController {
    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductService productService = new ProductService(new ProductDao(), new ProductValidator());

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        try {
            return Response.ok(productService.getAllProducts()).build();
        } catch (FailedToGetProductsException e) {
            logger.error("Product not found! Error: {}", e.getMessage());

            return Response.serverError().build();
        }
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsById(@PathParam("id") int id) {
        try {
            return Response.ok(productService.getProductById(id)).build();
        } catch (FailedToGetProductException e) {
            logger.error("Failed to get a product! Error: {}", e.getMessage());

            return Response.serverError().build();
        } catch (ProductDoesNotExistException e) {
            logger.error("Product does not exist! Id: {} Error: {}", id, e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(ProductRequest product) {
        try {
            return Response.ok(productService.createProduct(product)).build();
        } catch (FailedToCreateProductException e) {
            logger.error("Failed to create Product! Error: {}", e.getMessage());

            return Response.serverError().entity(new ErrorResponse(e.getMessage())).build();
        } catch (InvalidProductException e) {
            logger.error("Invalid Product data! Error: {}", e.getMessage());

            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }
    }
}
