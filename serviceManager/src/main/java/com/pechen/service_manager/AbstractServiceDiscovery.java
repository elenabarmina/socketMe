package com.pechen.service_manager;

import com.google.common.base.Predicate;
import org.junit.platform.commons.util.CollectionUtils;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;

/**
 * Created by pechen on 7/10/2017.
 */
public abstract class AbstractServiceDiscovery {

    private WebTarget userService;
    private WebTarget commandService;

    public abstract String getUserServiceURL();
    public abstract String getCommandServiceURL();

    public WebTarget getUserService() {
        if (null == userService) {
            userService = ClientBuilder
                    .newClient()
                    .target(URI.create(getUserServiceURL()));
        }

        return userService;
    }

    public WebTarget getCommandService() {
        if (null == commandService) {
            commandService = ClientBuilder
                    .newClient()
                    .target(URI.create(getCommandServiceURL()));
        }

        return commandService;
    }
}
