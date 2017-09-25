package com.pechen.socketme.beans;

import com.pechen.service_manager.ServiceDiscovery;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Created by Tom on 22.09.2017.
 */
@Named
@SessionScoped
public class CommandManager implements Serializable {

    @Inject
    ServiceDiscovery services;

    public boolean executeCommand(String name) {
        Response response = services.getUserService().request().post(Entity.json(name));

        Response.StatusType statusInfo = response.getStatusInfo();

        if (statusInfo.getFamily() == Response.Status.Family.SUCCESSFUL)
            return true;

        return false;
    }
}