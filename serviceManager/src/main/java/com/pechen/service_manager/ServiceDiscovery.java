package com.pechen.service_manager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by pechen on 7/10/2017.
 */
@Services
@ApplicationScoped
public class ServiceDiscovery extends AbstractServiceDiscovery{
    @Inject
    @Services
    ServiceRegistry services;

    @Override
    public String getUserServiceURL() {
        return services.discoverServiceURI("user");
    }
}
