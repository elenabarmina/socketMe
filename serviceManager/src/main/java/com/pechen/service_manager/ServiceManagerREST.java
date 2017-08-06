package com.pechen.service_manager;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import sun.misc.BASE64Decoder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pechen on 8/4/2017.
 */
@Path("/service")
public class ServiceManagerREST {

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

        services.registerService(inputService.getName(), inputService.getUrl());

        return Response.ok("ok").build();
    }

    @DELETE
    @Path("/unregister")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response unregister(String entity){

        Service service = getServiceFromJson(entity);
        services.unregisterService(service.getName(), service.getUrl());

        return Response.ok("ok").build();
    }

    @GET
    @Path("/get/{name}")
    public String getServiceUrl(@PathParam("name") String name){
        return services.discoverServiceURI(name);
    }

    protected boolean isAuth(String authString){
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

        return decodedAuth.equals("microServ:g4bdEt9");
    }

    protected boolean isTrustedUrl(String url){
        try {
            URL checkURL = new URL("http://" + url);
            URLConnection conn = checkURL.openConnection();
            conn.connect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    protected Service getServiceFromJson(String jsonString){
        Gson gson = new Gson();
        Service inputService = gson.fromJson(jsonString, Service.class);
        return inputService;
    }
}
