package com.pechen.socketme.beans;

import com.pechen.service_manager.ServiceDiscovery;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Created by pechen on 7/21/2017.
 */
@Named
@SessionScoped
public class UserManager implements Serializable {

    @Inject
    ServiceDiscovery services;

    public boolean authUser(String name) {
        Response response = services.getUserService().request().post(Entity.json(name));

        Response.StatusType statusInfo = response.getStatusInfo();

        if (statusInfo.getFamily() == Response.Status.Family.SUCCESSFUL)
            return true;

        return false;
    }

    public void logoutUser(String name){
        services.getUserService().path(name).request().delete();
    }
}
