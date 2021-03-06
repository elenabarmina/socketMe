package com.pechen.service_manager;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import sun.misc.BASE64Decoder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pechen on 8/4/2017.
 */
@Path("/service")
public class ServiceManagerREST {

    private static String AUTH_STRING = "microServ:g4bdEt9";

    @Inject
    @Services
    ServiceRegistry services;

    @PUT
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@HeaderParam("authorization") String authString,
                             String entity){

        if (!isAuth(authString))
            return Response.status(Response.Status.UNAUTHORIZED).build();

        Service inputService = getServiceFromJson(entity);

        if (!isTrustedUrl(inputService.getUrl()))
            return Response.serverError().entity("connection to service couldn't be established").build();

        if (!services.registerService(inputService.getName(), inputService.getUrl()))
            return Response.status(Response.Status.CONFLICT).build();;

        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/unregister")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response unregister(@HeaderParam("authorization") String authString,
                               String entity){

        if (!isAuth(authString))
            return Response.status(Response.Status.UNAUTHORIZED).build();

        Service inputService = getServiceFromJson(entity);

        if (!services.unregisterService(inputService.getName(), inputService.getUrl()))
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/get/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getServiceUrl(@HeaderParam("authorization") String authString,
                                  @PathParam("name") String name){

        if (!isAuth(authString))
            return Response.status(Response.Status.UNAUTHORIZED).build();

        String serviceUrl = services.discoverServiceURI(name);

        if (Strings.isNullOrEmpty(serviceUrl))
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(serviceUrl).build();

    }

    private boolean isAuth(String authString){
        if (Strings.isNullOrEmpty(authString)) return false;

        String decodedAuth = "";
        String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        byte[] bytes = null;
        try {
            bytes = new BASE64Decoder().decodeBuffer(authInfo);
        } catch (IOException e) {
            return false;
        }
        decodedAuth = new String(bytes);

        return decodedAuth.equals(AUTH_STRING);
    }

    private boolean isTrustedUrl(String url){
        try {
            URL checkURL = new URL("http://" + url);
            URLConnection conn = checkURL.openConnection();
            conn.connect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private Service getServiceFromJson(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Service.class);
    }
}
