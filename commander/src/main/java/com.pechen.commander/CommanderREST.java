package com.pechen.commander;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.StringReader;

/**
 * Created by pechen on 15.09.2017.
 */
@Stateless
@Path("commander")
public class CommanderREST {
    @GET
    @Path("/ping")
    public String create() {
        return "pong";
    }

    @GET
    @Consumes("application/json")
    public Response create(String rawData) {
        JsonObject jsonObject = Json.createReader(new StringReader(rawData)).readObject();

            return Response.status(Response.Status.OK)
                    .entity("{\"result\":\""+CommandExecutor.getInstance().executeCommand(jsonObject.getString("command"))+"\"}").build();

    }
}
