package com.pechen.user;

import java.io.StringReader;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


/**
 * Created by pechen on 7/10/2017.
 */
@Stateless
@Path("user")
public class UserREST {
    @POST
    @Consumes("application/json")
    public Response create(String rawData) {
        JsonObject jsonObject = Json.createReader(new StringReader(rawData)).readObject();

        if (UsersContainer.authNewUser(jsonObject.getString("name"))){
            return Response.status(Response.Status.CREATED)
                    .entity("{\"result\":\"created\"}").build();
        }else{
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"result\":\"user already exists\"}").build();
        }

    }

    @GET
    @Consumes("application/json")
    public String create() {
        return "hello";
    }

    @DELETE
    @Consumes("application/json")
    public Response remove(String rawData) {
        JsonObject jsonObject = Json.createReader(new StringReader(rawData)).readObject();

        if (UsersContainer.logoutUser(jsonObject.getString("name"))){
            return Response.status(Response.Status.OK)
                    .entity("{\"result\":\"created\"}").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"result\":\"user already exists\"}").build();
        }
    }
}
