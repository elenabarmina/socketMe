package com.pechen.service_manager;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by pechen on 8/4/2017.
 */
@Path("/service")
public class ServiceManagerREST {

    @Inject
    @Services
    ServiceRegistry services;

    @POST
    @Path("/register")
    public Response register(
        @QueryParam("name") String name,
        @QueryParam("url") String url,
        @HeaderParam("token") String token) {

        services.registerService(name, url);

            return Response
                    .status(200)
                    .entity("ok").build();
    }
}
