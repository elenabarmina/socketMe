package com.pechen.service_manager;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;

/**
 * Created by pechen on 7/10/2017.
 */
public abstract class AbstractServiceDiscovery {

    private WebTarget userService;

    public abstract String getUserServiceURL();

    public WebTarget getUserService() {
        if (null == userService) {
            userService = ClientBuilder
                    .newClient()
                    .target(URI.create(getUserServiceURL()));
        }

        return userService;
    }
}
