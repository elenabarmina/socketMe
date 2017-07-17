package com.pechen.user;

import java.io.StringReader;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

        return Response.ok(UsersContainer.authNewUser(jsonObject.getString("name"))).build();
    }



    @DELETE
    @Path("{name}")
    public void remove(@PathParam("name") String name) {
        UsersContainer.logoutUser(name);
    }
}
